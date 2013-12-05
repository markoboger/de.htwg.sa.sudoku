
package de.htwg.sudoku.controller.impl;

import java.util.Iterator;
import java.util.Set;

import com.google.inject.Inject;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.Step;
import de.htwg.sudoku.solverplugin.SolverPlugin;

public class GameCreator {
	
	private ISudokuController controller;
	private Set<SolverPlugin> plugins;
	
	@Inject
	public GameCreator(ISudokuController controller, Set<SolverPlugin> plugins) {
		this.controller = controller;
		this.plugins = plugins;
	}
	
	public  boolean findHardGame() {
		boolean hardGameFound = false;
		Step nextStep = null;
		while (!hardGameFound) {
			controller.create();
			controller.copy();
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
					if (controller.getGrid().isSymmetric()){System.out.println("Grid is symmetric");}
					hardGameFound = true;
					return hardGameFound;
					
				}
				gameSolved=controller.isSolved();
			}
			
		}
		controller.paste();
		return hardGameFound;
	}

}

