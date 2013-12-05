package de.htwg.sudoku.solver;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class NakedSingleSolver implements SolverPlugin {

	@Override
	public String getSolverName() {
		return "Find Naked Single";
	}

	@Override
	public boolean solveStep(ISudokuController controller) {
		IGrid grid = controller.getGrid();
		for (int row = 0; row < grid.getCellsPerEdge(); row++) {
			for (int column = 0; column < grid.getCellsPerEdge(); column++) {
				if (grid.candidates(row, column).cardinality() == 1 && grid.getICell(row, column).isUnSet()) {
					grid.getICell(row, column).setShowCandidates(true);
					grid.getICell(row, column).setHint(true);
					controller.setStatusLine("Found Naked Single at (" + row + "," + column + ")");
					controller.notifyObservers();
					return true;
				}
			}
		}
		controller.setStatusLine("No Naked Singles found");
		controller.notifyObservers();
		return false;
	}
	
	

}
