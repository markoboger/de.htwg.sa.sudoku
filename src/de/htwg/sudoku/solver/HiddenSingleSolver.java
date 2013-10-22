package de.htwg.sudoku.solver;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class HiddenSingleSolver implements SolverPlugin {

	@Override
	public String getSolverName() {
		return "Find Hidden Single";
	}

	@Override
	public boolean solve(ISudokuController controller) {
		// TODO Auto-generated method stub
		return false;
	}

}
