package de.htwg.sudoku.database.impl;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.sudoku.database.IGridDatabase;
import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.model.impl.Grid;

public class GridDb4oDatabase implements IGridDatabase {

	private ObjectContainer db;

	public GridDb4oDatabase() {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				"target/grid.data");
	}

	@Override
	public void saveGrid(final IGrid grid) {
		db.store(grid);
	}

	private boolean containsGrid(final IGrid grid) {
		final String gridString = grid.toString();
		List<IGrid> grids = db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (grid.toString().equals(gridString));
			}
		});

		if (grids.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<IGrid> getGridsByDifficulty(final int max, final int min) {
		return db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (min <= grid.getNumberSetCells() && grid
						.getNumberSetCells() <= max);
			}
		});
	}

	@Override
	public void deleteGrid(final IGrid grid) {
		db.delete(grid);
	}

	@Override
	public List<IGrid> getAllGrids() {
		return db.query(IGrid.class);
	}

	public void closeDb() {
		db.close();
	}

	@Override
	public void generateGrids(int number, int gridSize) {
		for (int i = 0; i < number; i++) {
			IGrid grid = new Grid(gridSize);
			grid.create();
			if (containsGrid(grid)) {
				number--;
			} else {
				db.store(grid);
			}
		}
	}

	@Override
	public boolean containsGridByName(final String name) {
		List<IGrid> grids = db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (grid.getName().equals(name));
			}
		});

		if (grids.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public IGrid getGrid(final String name, final int setCells) {
		List<IGrid> grids = db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (setCells == grid.getNumberSetCells() && grid.getName().equals(name));
			}
		});

		if (grids.size() > 0) {
			return grids.get(0);
		}
		return null;
	}
}
