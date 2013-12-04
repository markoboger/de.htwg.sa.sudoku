package de.htwg.sudoku.solver;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.model.Step;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class HiddenSingleSolver implements SolverPlugin {

	@Override
	public String getSolverName() {
		return "Find Hidden Single";
	}

	@Override
	public Step solveStep(ISudokuController controller) {
		IGrid grid = controller.getGrid();
		for (int row = 0; row < controller.getCellsPerRow(); row++) {
			for (int value = 1; value <= controller.getCellsPerRow(); value++) {
				int counter = 0;
				int hiddenval = 0;
				int hiddencol = 0;
				
				for (int column = 0; column < controller.getCellsPerRow(); column++) {
					if (!grid.getICell(row, column).isSet() && grid.candidates(row, column).get(value) == true) {
						hiddenval = value;
						counter++;
						if (counter == 1) { 
							hiddencol = column;
						}
					}
				}
				if (counter == 1) {
					controller.setStatusLine("Found Hidden Single for value " + hiddenval + "  at (" + row + ","+ hiddencol + ")");
					controller.notifyObservers();
					return new Step(row, hiddencol, hiddenval,"Found Hidden Single for value " + hiddenval + "  at (" + row + ","+ hiddencol + ")");
				}
			}
			
		}
		controller.setStatusLine("No Hidden Singles found");
		controller.notifyObservers();
		return null;

	}

}
