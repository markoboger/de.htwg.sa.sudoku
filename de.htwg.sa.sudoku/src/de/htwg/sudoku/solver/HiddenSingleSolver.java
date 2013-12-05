package de.htwg.sudoku.solver;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.Step;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class HiddenSingleSolver implements SolverPlugin {

	@Override
	public String getSolverName() {
		return "Find Hidden Single";
	}

	@Override

	public Step solveStep(ISudokuController controller) {

		return null;
	}

}
