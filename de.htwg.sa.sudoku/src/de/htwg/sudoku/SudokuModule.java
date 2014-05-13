package de.htwg.sudoku;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.htwg.sudoku.controller.ISudokuController;
import de.htwg.sudoku.model.IGridFactory;
import de.htwg.sudoku.persistence.IGridDAO;
import de.htwg.sudoku.solver.HiddenSingleSolver;
import de.htwg.sudoku.solver.NakedSingleSolver;
import de.htwg.sudoku.solverplugin.SolverPlugin;


public class SudokuModule extends AbstractModule {

	
	@Override
	protected void configure() {

		bind(IGridFactory.class).to(de.htwg.sudoku.model.impl.GridFactory.class);
		bind(ISudokuController.class).to(de.htwg.sudoku.controller.logwrapper.SudokuController.class);
//		bind(IGridDAO.class).to(de.htwg.sudoku.persistence.couchdb.GridCouchdbDAO.class);
//		bind(IGridDAO.class).to(de.htwg.sudoku.persistence.db4o.GridDb4oDAO.class);
		bind(IGridDAO.class).to(de.htwg.sudoku.persistence.hibernate.GridHibernateDAO.class);
		Multibinder<SolverPlugin> plugins = Multibinder.newSetBinder(binder(), SolverPlugin.class);
		plugins.addBinding().to(NakedSingleSolver.class);
		plugins.addBinding().to(HiddenSingleSolver.class);
	}
}
