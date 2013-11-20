package de.htwg.sudoku.controller.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;

import javax.swing.undo.UndoManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.controller.SetValueCommand;
import de.htwg.sudoku.controller.SizeChangedEvent;
import de.htwg.sudoku.model.ICell;
import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.model.IGridFactory;
import de.htwg.sudoku.persistence.IGridDAO;
import de.htwg.util.observer.Event;
import de.htwg.util.observer.Observable;

@Singleton
public class SudokuController extends Observable implements ISudokuController {
	
	private String statusLine = "Welcome to HTWG Sudoku!";
	private IGrid grid;
	private IGridFactory gridFactory;
	private UndoManager undoManager;
	private int highlighted=0;
	private static final int NORMALGRID=3;
	private IGridDAO gridDAO;
	
	@Inject
	public SudokuController(IGridFactory gridFactory, IGridDAO gridDAO) {
		this.gridFactory=gridFactory;
		this.grid = gridFactory.create(NORMALGRID);
		this.undoManager = new UndoManager();
		this.gridDAO = gridDAO;
	}
	
	public void setValue(int row, int column, int value) {
		ICell cell = grid.getICell(row, column);
		if (cell.isUnSet()) {
			cell.setValue(value);
			undoManager.addEdit(new SetValueCommand(cell));
			statusLine = "The cell " + cell.mkString() + " was successfully set";
		} else {
			statusLine="The cell " + cell.mkString() + " is already set";
		}
		notifyObservers();
	}
	
	public void solve() {
		boolean result;
		result = grid.solve();		
		if (result) {
			statusLine="The Sudoku was solved successfully";
		} else {
			statusLine="Can not solve this Sudoku within "
					+ grid.getSteps() + " steps";
		}
		notifyObservers();
	}
	public void reset() {
		grid.reset();
		statusLine = "Sudoku was reset";
		notifyObservers();
	}
	
	public void create() {
		this.grid = gridFactory.create(grid.getBlockSize());
		grid.create();
		highlighted=0;
		statusLine= "New Sudoku Puzzle created";
		notifyObservers();
	}

	public String getStatus() {
		return statusLine;
	}

	public String getGridString() {
		return grid.toString();
	}
	
	public void undo() {
		if (undoManager.canUndo()){
			undoManager.undo();
		}
		statusLine = "Undo";
		notifyObservers();
	}

	public void redo() {
		if (undoManager.canRedo()){
			undoManager.redo();
		}
		statusLine= "Redo";
		notifyObservers();
	}
	
	public void copy() {
		StringSelection gridString = new StringSelection(grid.toString("0"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				gridString, null);
		statusLine= "Copied Sudoku";
		notifyObservers();
	}

	public void paste() {
		Transferable transferable = Toolkit.getDefaultToolkit()
				.getSystemClipboard().getContents(null);
		if (transferable != null
				&& transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			String input;
			try {
				input = (String) transferable
						.getTransferData(DataFlavor.stringFlavor);
				grid.parseStringToGrid(input);
			} catch (UnsupportedFlavorException e1) {

				statusLine = "Could not read from Clipboard";
			} catch (IOException e1) {

				statusLine = "Could not read from Clipboard";
			}
		}
		statusLine= "Pasted Sudoku";
		notifyObservers();
	}

	public int getValue(int row, int column) {
		return grid.getICell(row, column).getValue();
	}

	public void showCandidates(int row, int column) {
		grid.getICell(row, column).toggleShowCandidates();
		BitSet set = grid.candidates(row,column);
		statusLine = "Candidates at ("+row+","+column+") are "+set.toString();
		notifyObservers();
	}

	public void highlight(int value) {
		highlighted=value;
		notifyObservers();
	}

	public int getCellsPerRow() {
		return grid.getCellsPerEdge();
	}

	public int getBlockSize() {
		return grid.getBlockSize();
	}

	public int blockAt(int row, int column) {
		return grid.blockAt(row, column);
	}

	public void exit() {
		System.exit(0);
	}

	public void showAllCandidates() {
		for (int row = 0; row < grid.getCellsPerEdge(); row++) {
			for (int col = 0; col < grid.getCellsPerEdge(); col++) {
				showCandidates(row, col);
			}	
		}
		notifyObservers();
	}

	public boolean isGiven(int row, int column) {
		return grid.getICell(row, column).isGiven();
	}

	public boolean isHighlighted(int row, int column) {
		return grid.candidates(row, column).get(highlighted);
	}

	public boolean isSet(int row, int column) {
		return grid.getICell(row, column).isSet();
	}

	public boolean isShowCandidates(int row, int column) {
		return grid.getICell(row, column).isShowCandidates();
	}

	public boolean isCandidate(int row, int column, int candidate) {
		return grid.candidates(row, column).get(candidate);
	}

	@Override
	public void parseStringToGrid(String gridString) {
		grid.parseStringToGrid(gridString);
		notifyObservers();
	}

	@Override
	public void resetSize(int newSize) {
		this.grid = gridFactory.create(newSize);
		reset();
		Event event = new SizeChangedEvent(); 
		notifyObservers(event);
	}

	public IGrid getGrid() {
		return grid;
	}

	@Override
	public void loadFromDB(String gridId) {
		this.grid = gridDAO.getGridById(gridId);
		this.highlighted = 0;
		this.statusLine = "Sudoku Puzzle loaded";
		notifyObservers();
	}

	@Override
	public void saveToDB() {
		gridDAO.saveGrid(grid);
	}

	@Override
	public String[][] getRowDataAll() {
		List<IGrid> grids = gridDAO.getAllGrids();
		String[][] data = new String[grids.size()][3];
		for(int i = 0; i < grids.size(); i++) {
			IGrid g = grids.get(i);
			data[i][0] = g.getName();
			data[i][1] = String.valueOf(g.getNumberSetCells());
			data[i][2] = g.getId().toString();
		}
		return data;
	}

	@Override
	public String[][] getRowData(int min, int max) {
		List<IGrid> grids = gridDAO.getGridsByDifficulty(max, min);
		String[][] data = new String[grids.size()][3];
		for(int i = 0; i < grids.size(); i++) {
			IGrid g = grids.get(i);
			data[i][0] = g.getName();
			data[i][1] = String.valueOf(g.getNumberSetCells());
			data[i][2] = g.getId().toString();
		}
		return data;
	}

	@Override
	public String getGridName() {
		return grid.getName();
	}

	@Override
	public void setGridName(String name) {
		grid.setName(name);		
	}

	@Override
	public void generateGridToDB(int number) {
		gridDAO.generateGrids(number, NORMALGRID);		
	}

	@Override

	public boolean isHint(int row, int column) {
		return grid.getICell(row, column).isHint();
	}

	@Override
	public void setStatusLine(String string) {
		statusLine=string;	
	}

	public boolean containsActualGridDB() {
		return gridDAO.containsGridById(grid.getId());

	}

	@Override
	public void deleteFromDB(String id) {
		gridDAO.deleteGridById(id);		
	}

	@Override
	public JSONObject getHighscores() {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:9000");
		
		try {
			HttpResponse response = client.execute(request);
			JSONTokener tokener = new JSONTokener(response.getEntity().getContent());
			return new JSONObject(tokener);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void addHighscore(String player, long score) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:9000/addHighscore?game=Sudoku&player=" + player + "&score=" + score);
		try {
			client.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
