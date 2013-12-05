package de.htwg.sudoku.solverplugin;

import de.htwg.sudoku.controller.ISudokuController;

public interface SolverPlugin {
	
	String getSolverName();
	
	boolean solveStep(ISudokuController controller);

}
