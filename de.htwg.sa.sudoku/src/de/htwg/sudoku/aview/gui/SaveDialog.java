package de.htwg.sudoku.aview.gui;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.sudoku.controller.ISudokuController;

class SaveDialog extends JDialog implements ActionListener,
		PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ISudokuController controller;
	private JTextField textField;

	private JOptionPane optionPane;

	private String btnString1 = "Save";
	private String btnString2 = "Cancel";

	/** Creates the reusable dialog. */
	public SaveDialog(Frame aFrame, ISudokuController controller) {
		super(aFrame, true);

		this.controller = controller;

		setTitle("Save game...");

		JPanel contentPanel = new JPanel(new FlowLayout(10));
		contentPanel.add(new JLabel("Name:"));
		textField = new JTextField(10);
		textField.setText(controller.getGridName());
		contentPanel.add(textField);
		
		Object[] options = { btnString1, btnString2 };

		// Create the JOptionPane.
		optionPane = new JOptionPane(contentPanel, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);

		setContentPane(optionPane);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});

		textField.addActionListener(this);
		optionPane.addPropertyChangeListener(this);
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(btnString1);
	}

	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY
						.equals(prop))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (btnString1.equals(value)) {
				controller.setGridName(textField.getText());
				controller.saveToDB();
			}
			clearAndHide();
		}
	}

	public void clearAndHide() {
		textField.setText(null);
		setVisible(false);
	}
}
