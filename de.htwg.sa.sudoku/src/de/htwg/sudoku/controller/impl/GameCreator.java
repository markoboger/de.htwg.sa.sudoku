package de.htwg.sudoku.controller.impl;

import de.htwg.sudoku.controller.ISudokuController;

public class GameCreator {
	
	public static boolean findHardGame(ISudokuController controller) {
		boolean hardGameFound = false;
		while (!hardGameFound) {
			controller.create();
			boolean gameSolved=false;
			while(!gameSolved) {
				if (controller.findSolveStep()) { 
					controller.doSolveStep();
				} else {
					hardGameFound = true;
					return hardGameFound;
				}
				gameSolved=controller.isSolved();
			}
			
		}
		return hardGameFound;
	}

}
