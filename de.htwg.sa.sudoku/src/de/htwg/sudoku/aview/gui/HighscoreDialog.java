package de.htwg.sudoku.aview.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import de.htwg.sudoku.controller.ISudokuController;

public class HighscoreDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private String[] columnNames = { "Game", "Player", "Score"};
	private ISudokuController controller;
	private String[][] rowData;
	
	public HighscoreDialog(final ISudokuController controller) {
		this.controller = controller;
		this.rowData = controller.getRowDataAll();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
		tablePanel.setBorder(BorderFactory.createTitledBorder("Highscores"));
		tablePanel.add(new JScrollPane(table));
		mainPanel.add(tablePanel, BorderLayout.PAGE_END);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,
				10));
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HighscoreDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(exitButton);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		updateTable();

		this.setContentPane(mainPanel);
		this.setResizable(false);
		this.setTitle("Show Highscores");
		this.pack();
		this.setVisible(true);
	}

	private void updateTable() {
		JSONObject highscores = controller.getHighscores();
		
		JSONArray scoreArray = (JSONArray) highscores.get("result");
		
		System.out.println(scoreArray);
		
		rowData = new String[scoreArray.length()][3];
		for (int i=0; i<scoreArray.length(); i++) {
		    JSONObject item = scoreArray.getJSONObject(i);
		    rowData[i][0] = item.getString("game");
		    rowData[i][1] = item.getString("player");
		    rowData[i][2] = String.valueOf(item.getLong("score"));
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
