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

	private JTable table;
	private String[] columnNames = { "Name", "Difficulty" };
	private ISudokuController controller;
	private JComboBox<String> diffCombo;
	

	public LoadDialog(final ISudokuController controller) {
		this.controller = controller;

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel optionsPanel = new JPanel(new FlowLayout(10));
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
				controller.generateGridToDB(20);
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

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,
				10));
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
				String name = (String) LoadDialog.this.table.getValueAt(rowIdx,
						0);
				String setCells = (String) LoadDialog.this.table.getValueAt(
						rowIdx, 1);
				controller.loadFromDB(name, Integer.parseInt(setCells));
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

	protected void updateTable() {
		String[][] rowData;
		
		String difficulty = (String) diffCombo.getSelectedItem();
		if (difficulty.equals("Easy")) {
			rowData = controller.getRowData(55, 81);
		} else if (difficulty.equals("Medium")) {
			rowData = controller.getRowData(40, 54);
		} else if (difficulty.equals("Hard")) {
			rowData = controller.getRowData(0, 39);
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
