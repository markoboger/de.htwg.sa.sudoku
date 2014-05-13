package de.htwg.sudoku.controller.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.sudoku.SudokuModule;
import de.htwg.sudoku.controller.ISudokuController;

public class GameCreatorTest {
	
	private GameCreator creator;
	private ISudokuController controller;

	public GameCreatorTest() {
		super();
		Injector injector = Guice.createInjector(new SudokuModule());
		controller = injector.getInstance(ISudokuController.class);
		creator = injector.getInstance(GameCreator.class);
		
	}

//	@Test
//	public void testFindHardGame() {
//		creator.findHardGame();
//		System.out.println(controller.getGrid().toString());
//		assertFalse(controller.getGrid().isSolved());
//		assertTrue(controller.getGrid().isSymmetric());
//			
//			
//		
//	}

}
