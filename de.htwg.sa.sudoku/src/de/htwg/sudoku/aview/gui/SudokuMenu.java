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
	
	private ISudokuController controller;
	private JFrame frame;
	
	public SudokuMenu(JFrame frame, ISudokuController controller) {
		this.frame = frame;
		this.controller = controller;
	}
	JMenu file ;
	JMenuItem  item_newSudoku, item_save, item_load, item_save_to_DB, item_load_from_DB, item_exit ; 
	
	JMenu edit ;
	JMenuItem  item_undo, item_redo, item_copy, item_paste ; 
	
	JMenu solve ;
	JMenuItem  item_solve_Now ; 
	
	JMenu highlight ;
	JMenuItem  item_none, item_1, item_2, item_3, item_4, item_5, item_6, item_7, item_8, item_9 ; 
	
	JMenu options ;
	JMenuItem  item_toggle_Show_Candidates, item_resize_to_9_by_9, item_resize_to_4_by_4, item_resize_to_1_by_1 ; 
	
	
	
	public JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		file = new JMenu("File ");
		file.setMnemonic(KeyEvent.VK_F); 
		item_newSudoku = new JMenuItem("NewSudoku");
		item_newSudoku.setMnemonic(KeyEvent.VK_N); 
		item_newSudoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.create();
			}
		});
		file.add(item_newSudoku);
		item_save = new JMenuItem("Save");
		item_save.setMnemonic(KeyEvent.VK_S); 
		item_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save(frame);
			}
		});
		file.add(item_save);
		item_load = new JMenuItem("Load");
		item_load.setMnemonic(KeyEvent.VK_L); 
		item_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load(frame);
			}
		});
		file.add(item_load);
		item_save_to_DB = new JMenuItem("Save to DB");
		item_save_to_DB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SaveDialog(frame,controller);
			}
		});
		file.add(item_save_to_DB);
		item_load_from_DB = new JMenuItem("Load from DB");
		item_load_from_DB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LoadDialog(controller);
			}
		});
		file.add(item_load_from_DB);
		item_exit = new JMenuItem("Exit");
		item_exit.setMnemonic(KeyEvent.VK_X); 
		item_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.exit();
			}
		});
		file.add(item_exit);
		menuBar.add(file);
		edit = new JMenu("Edit ");
		edit.setMnemonic(KeyEvent.VK_E); 
		item_undo = new JMenuItem("Undo");
		item_undo.setMnemonic(KeyEvent.VK_U); 
		item_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				InputEvent.CTRL_DOWN_MASK)); 
		item_undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		edit.add(item_undo);
		item_redo = new JMenuItem("Redo");
		item_redo.setMnemonic(KeyEvent.VK_R); 
		item_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				InputEvent.CTRL_DOWN_MASK)); 
		item_redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});
		edit.add(item_redo);
		item_copy = new JMenuItem("Copy");
		item_copy.setMnemonic(KeyEvent.VK_C); 
		item_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_DOWN_MASK)); 
		item_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.copy();
			}
		});
		edit.add(item_copy);
		item_paste = new JMenuItem("Paste");
		item_paste.setMnemonic(KeyEvent.VK_P); 
		item_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_DOWN_MASK)); 
		item_paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.paste();
			}
		});
		edit.add(item_paste);
		menuBar.add(edit);
		solve = new JMenu("Solve ");
		solve.setMnemonic(KeyEvent.VK_S); 
		item_solve_Now = new JMenuItem("Solve Now");
		item_solve_Now.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.solve();
			}
		});
		solve.add(item_solve_Now);
		menuBar.add(solve);
		highlight = new JMenu("Highlight ");
		highlight.setMnemonic(KeyEvent.VK_H); 
		item_none = new JMenuItem("None");
		item_none.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=0;
				controller.highlight(DIGIT);
			}
		});
		highlight.add(item_none);
		item_1 = new JMenuItem("1");
		item_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=1;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_1);
		item_2 = new JMenuItem("2");
		item_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=2;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_2);
		item_3 = new JMenuItem("3");
		item_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=3;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_3);
		item_4 = new JMenuItem("4");
		item_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=4;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_4);
		item_5 = new JMenuItem("5");
		item_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=5;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_5);
		item_6 = new JMenuItem("6");
		item_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=6;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_6);
		item_7 = new JMenuItem("7");
		item_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=7;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_7);
		item_8 = new JMenuItem("8");
		item_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=8;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_8);
		item_9 = new JMenuItem("9");
		item_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    final int DIGIT=9;
                controller.highlight(DIGIT);
			}
		});
		highlight.add(item_9);
		menuBar.add(highlight);
		options = new JMenu("Options ");
		options.setMnemonic(KeyEvent.VK_O); 
		item_toggle_Show_Candidates = new JMenuItem("Toggle Show Candidates");
		item_toggle_Show_Candidates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.showAllCandidates();
			}
		});
		options.add(item_toggle_Show_Candidates);
		item_resize_to_9_by_9 = new JMenuItem("Resize to 9 by 9");
		item_resize_to_9_by_9.setMnemonic(KeyEvent.VK_NUMBER_SIGN); 
		item_resize_to_9_by_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(3);
			}
		});
		options.add(item_resize_to_9_by_9);
		item_resize_to_4_by_4 = new JMenuItem("Resize to 4 by 4");
		item_resize_to_4_by_4.setMnemonic(KeyEvent.VK_PLUS); 
		item_resize_to_4_by_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(2);
			}
		});
		options.add(item_resize_to_4_by_4);
		item_resize_to_1_by_1 = new JMenuItem("Resize to 1 by 1");
		item_resize_to_1_by_1.setMnemonic(KeyEvent.VK_PERIOD); 
		item_resize_to_1_by_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSize(1);
			}
		});
		options.add(item_resize_to_1_by_1);
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
