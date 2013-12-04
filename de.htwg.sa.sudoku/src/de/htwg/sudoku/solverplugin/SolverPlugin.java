package de.htwg.sudoku.solverplugin;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.Step;

public interface SolverPlugin {
	
	String getSolverName();
	
	Step solveStep(ISudokuController controller);

}
