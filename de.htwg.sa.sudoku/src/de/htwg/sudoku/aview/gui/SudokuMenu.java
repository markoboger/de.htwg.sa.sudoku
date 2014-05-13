package de.htwg.sudoku.aview.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import de.htwg.sudoku.controller.ISudokuController;

public class SudokuMenu {
	
	private static final int SIZE1BY1 = 1;
    private static final int SIZE4BY4 = 2;
    private static final int SIZE9BY9 = 3;
    private ISudokuController controller;
	private JFrame frame;
	
	public SudokuMenu(JFrame frame, ISudokuController controller) {
		this.frame = frame;
		this.controller = controller;
	}

	
	
	public JMenuBar buildMenuBar() {
	    
	    JMenu file ;
	    JMenuItem  itemNewSudoku, itemSave, itemLoad, itemSaveToDB, itemLoadFromDB, itemExit ; 
	    
	    JMenu edit ;
	    JMenuItem  itemUndo, itemRedo, itemCopy, itemPaste ; 
	    
	    JMenu solve ;
	    JMenuItem  itemSolveNow ; 
	    
	    JMenu highlight ;
	    JMenuItem  itemNone, item1, item2, item3, item4, item5, item6, item7, item8, item9 ; 
	    
	    JMenu options ;
	    JMenuItem  itemToggleShowCandidates, itemResizeTo9by9, itemResizeTo4by4, itemResizeTo1by1 ; 
	    
		JMenuBar menuBar = new JMenuBar();
		file = new JMenu("File ");
		file.setMnemonic(KeyEvent.VK_F); 
		itemNewSudoku = new JMenuItem("NewSudoku");
		itemNewSudoku.setMnemonic(KeyEvent.VK_N); 
		itemNewSudoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.create();
			}
		});
		file.add(itemNewSudoku);
		itemSave = new JMenuItem("Save");
		itemSave.setMnemonic(KeyEvent.VK_S); 
		itemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save(frame);
			}
		});
		file.add(itemSave);
		itemLoad = new JMenuItem("Load");
		itemLoad.setMnemonic(KeyEvent.VK_L); 
		itemLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load(frame);
			}
		});
		file.add(itemLoad);
		itemSaveToDB = new JMenuItem("Save to DB");
		itemSaveToDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SaveDialog(frame,controller);
			}
		});
		file.add(itemSaveToDB);
		itemLoadFromDB = new JMenuItem("Load from DB");
		itemLoadFromDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LoadDialog(controller);
			}
		});
		file.add(itemLoadFromDB);
		itemExit = new JMenuItem("Exit");
		itemExit.setMnemonic(KeyEvent.VK_X); 
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.exit();
			}
		});
		file.add(itemExit);
		menuBar.add(file);
		edit = new JMenu("Edit ");
		edit.setMnemonic(KeyEvent.VK_E); 
		itemUndo = new JMenuItem("Undo");
		itemUndo.setMnemonic(KeyEvent.VK_U); 
		itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				InputEvent.CTRL_DOWN_MASK)); 
		itemUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		edit.add(itemUndo);
		itemRedo = new JMenuItem("Redo");
		itemRedo.setMnemonic(KeyEvent.VK_R); 
		itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				InputEvent.CTRL_DOWN_MASK)); 
		itemRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});
		edit.add(itemRedo);
		itemCopy = new JMenuItem("Copy");
		itemCopy.setMnemonic(KeyEvent.VK_C); 
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_DOWN_MASK)); 
		itemCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.copy();
			}
		});
		edit.add(itemCopy);
		itemPaste = new JMenuItem("Paste");
		itemPaste.setMnemonic(KeyEvent.VK_P); 
		itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_DOWN_MASK)); 
		itemPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.paste();
			}
		});
		edit.add(itemPaste);
		menuBar.add(edit);
		solve = new JMenu("Solve ");
		solve.setMnemonic(KeyEvent.VK_S); 
		itemSolveNow = new JMenuItem("Solve Now");
		itemSolveNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.solve();
			}
		});
		solve.add(itemSolveNow);
		menuBar.add(solve);
		highlight = new JMenu("Highlight ");
		highlight.setMnemonic(KeyEvent.VK_H); 
		itemNone = new JMenuItem("None");
		itemNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=0;
				controller.highlight(digit);
			}
		});
		highlight.add(itemNone);
		item1 = new JMenuItem("1");
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=1;
                controller.highlight(digit);
			}
		});
		highlight.add(item1);
		item2 = new JMenuItem("2");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=2;
                controller.highlight(digit);
			}
		});
		highlight.add(item2);
		item3 = new JMenuItem("3");
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=3;
                controller.highlight(digit);
			}
		});
		highlight.add(item3);
		item4 = new JMenuItem("4");
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=4;
                controller.highlight(digit);
			}
		});
		highlight.add(item4);
		item5 = new JMenuItem("5");
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=5;
                controller.highlight(digit);
			}
		});
		highlight.add(item5);
		item6 = new JMenuItem("6");
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=6;
                controller.highlight(digit);
			}
		});
		highlight.add(item6);
		item7 = new JMenuItem("7");
		item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=7;
                controller.highlight(digit);
			}
		});
		highlight.add(item7);
		item8 = new JMenuItem("8");
		item8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=8;
                controller.highlight(digit);
			}
		});
		highlight.add(item8);
		item9 = new JMenuItem("9");
		item9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int digit=9;
                controller.highlight(digit);
			}
		});
		highlight.add(item9);
		menuBar.add(highlight);
		options = new JMenu("Options ");
		options.setMnemonic(KeyEvent.VK_O); 
		itemToggleShowCandidates = new JMenuItem("Toggle Show Candidates");
		itemToggleShowCandidates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.showAllCandidates();
			}
		});
		options.add(itemToggleShowCandidates);
		itemResizeTo9by9 = new JMenuItem("Resize to 9 by 9");
		itemResizeTo9by9.setMnemonic(KeyEvent.VK_NUMBER_SIGN); 
		itemResizeTo9by9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(SIZE9BY9);
			}
		});
		options.add(itemResizeTo9by9);
		itemResizeTo4by4 = new JMenuItem("Resize to 4 by 4");
		itemResizeTo4by4.setMnemonic(KeyEvent.VK_PLUS); 
		itemResizeTo4by4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(SIZE4BY4);
			}
		});
		options.add(itemResizeTo4by4);
		itemResizeTo1by1 = new JMenuItem("Resize to 1 by 1");
		itemResizeTo1by1.setMnemonic(KeyEvent.VK_PERIOD); 
		itemResizeTo1by1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(SIZE1BY1);
			}
		});
		options.add(itemResizeTo1by1);
		menuBar.add(options);
		return menuBar;
	}
		public void load(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(".");
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream fis = new FileInputStream(
						fileChooser.getSelectedFile());
				ObjectInputStream inStream = new ObjectInputStream(fis);
				controller.parseStringToGrid((String) inStream.readObject());
				inStream.close();
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(
						frame,
						"IOException reading sudoku:\n"
								+ ioe.getLocalizedMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
	
			}
		}
	}
	
		public void save(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(".");
		int result = fileChooser.showSaveDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()
					&& JOptionPane.showConfirmDialog(frame,
							"File \"" + file.getName() + "\" already exists.\n"
									+ "Would you like to replace it?", "Save",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream outStream = new ObjectOutputStream(fos);
				outStream.writeObject(controller.getGridString());
				outStream.flush();
				outStream.close();
	
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(
						frame,
						"IOException saving sudoku:\n"
								+ ioe.getLocalizedMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
