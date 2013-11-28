package de.htwg.sudoku.controller.impl;

import java.util.Iterator;
import java.util.Set;

import com.google.inject.Inject;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.Step;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class GameCreator {
	
	@Inject
	public static boolean findHardGame(ISudokuController controller, Set<SolverPlugin> plugins) {
		boolean hardGameFound = false;
		Step nextStep = null;
		while (!hardGameFound) {
			controller.create();
			boolean gameSolved=false;
			while(!gameSolved) {
				Iterator<SolverPlugin> iter = plugins.iterator();
				while (iter.hasNext()) {
					final SolverPlugin plugin = iter.next();
					nextStep = plugin.solveStep(controller);
				}
				if (nextStep!=null) { 
					controller.setValue(nextStep.getRow(), nextStep.getColumn(), nextStep.getValue());
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

