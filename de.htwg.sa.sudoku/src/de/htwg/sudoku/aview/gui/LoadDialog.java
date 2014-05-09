package de.htwg.sudoku.aview.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.htwg.sudoku.controller.ISudokuController;

public class LoadDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int BEVEL=10;
	private static final int BUTTONS=10;

	private JTable table;
	private String[] columnNames = { "Name", "Difficulty"};
	private ISudokuController controller;
	private JComboBox<String> diffCombo;
	private String[][] rowData;
	
	public LoadDialog(final ISudokuController controller) {
		this.controller = controller;
		this.rowData = controller.getRowDataAll();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(BEVEL, BEVEL, BEVEL, BEVEL));

		JPanel optionsPanel = new JPanel(new FlowLayout(BUTTONS));
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		JLabel diffLabel = new JLabel("Difficulty: ");
		diffCombo = new JComboBox<String>();
		diffCombo.addItem("All");
		diffCombo.addItem("Easy");
		diffCombo.addItem("Medium");
		diffCombo.addItem("Hard");
		diffCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateTable();
			}
		});
		
		JButton genButton = new JButton("Generate some playgrounds");
		genButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    final int numberOfGrids=20;
				controller.generateGridToDB(numberOfGrids);
				updateTable();
			}
		});
		optionsPanel.add(genButton);
		optionsPanel.add(diffLabel);
		optionsPanel.add(diffCombo);
		mainPanel.add(optionsPanel, BorderLayout.PAGE_START);

		JPanel tablePanel = new JPanel();
		table = new JTable();
		table.getColumnModel().setSelectionModel(
				new DefaultListSelectionModel() {
					private static final long serialVersionUID = 1L;

					@Override
					public int getLeadSelectionIndex() {
						return -1;
					}
				});
		tablePanel.setBorder(BorderFactory.createTitledBorder("Games"));
		tablePanel.add(new JScrollPane(table));
		mainPanel.add(tablePanel, BorderLayout.PAGE_END);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTONS,
				BUTTONS));
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int rowIdx = LoadDialog.this.table.getSelectedRow();
				if (rowIdx == -1) {
					JOptionPane.showMessageDialog(LoadDialog.this,
							"Please choose an entry first.");
					return;
				}
				String id = (String) LoadDialog.this.rowData[rowIdx][2];
				controller.deleteFromDB(id);
				LoadDialog.this.updateTable();
			}
		});
		buttonPanel.add(deleteButton);		
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int rowIdx = LoadDialog.this.table.getSelectedRow();
				if (rowIdx == -1) {
					JOptionPane.showMessageDialog(LoadDialog.this,
							"Please choose an entry first.");
					return;
				}
				String id = (String) LoadDialog.this.rowData[rowIdx][2];
				controller.loadFromDB(id);
				LoadDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(loadButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoadDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(exitButton);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		updateTable();

		this.setContentPane(mainPanel);
		this.setResizable(false);
		this.setTitle("Load a Grid from Database");
		this.pack();
		this.setVisible(true);
	}

	private static final int EASY=81;
	private static final int MEDIUM=54;
	private static final int HARD=39;
	
	private void updateTable() {		
		String difficulty = (String) diffCombo.getSelectedItem();
		if (difficulty.equals("Easy")) {
			rowData = controller.getRowData(MEDIUM+1, EASY);
		} else if (difficulty.equals("Medium")) {
			rowData = controller.getRowData(HARD+1, MEDIUM);
		} else if (difficulty.equals("Hard")) {
			rowData = controller.getRowData(0, HARD);
		} else {
			rowData = controller.getRowDataAll();
		}

		table.setModel(new DefaultTableModel(rowData, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}
}
