package de.htwg.sudoku.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.persistence.IGridDAO;

public class GridCouchdbDAO implements IGridDAO {

	private CouchDbConnector db = null;

	public GridCouchdbDAO() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url(
					"http://lenny2.in.htwg-konstanz.de:5984").build();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector("sudoku_db", true);
	}

	@Override
	public void saveGrid(IGrid grid) {
		if (containsGridById(grid.getId())) {
			db.update(grid);
		} else {
			db.create(grid.getId(), grid);
		}
	}

	@Override
	public boolean containsGridById(String id) {
		if (getGridById(id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<IGrid> getGridsByDifficulty(int max, int min) {
		List<IGrid> l = getAllGrids();
		for (IGrid g : l) {
			int diff = g.getNumberSetCells();
			if (diff > max || diff < min) {
				l.remove(g);
			}
		}
		return l;
	}

	@Override
	public IGrid getGridById(String id) {
		IGrid g = db.find(IGrid.class, id);
		if (g == null) {
			return null;
		}
		return g;
	}

	@Override
	public void deleteGridById(String id) {
		db.delete(getGridById(id));
	}

	@Override
	public List<IGrid> getAllGrids() {
		List<IGrid> lst = new ArrayList<IGrid>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = db.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(getGridById(r.getId()));
		}
		return lst;
	}

	@Override
	public void generateGrids(int number, int gridSize) {
		// TODO Auto-generated method stub

	}
}
