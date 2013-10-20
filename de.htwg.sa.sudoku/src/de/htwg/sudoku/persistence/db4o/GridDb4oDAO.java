package de.htwg.sudoku.persistence.db4o;

import java.util.List;
import java.util.UUID;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.model.impl.Grid;
import de.htwg.sudoku.persistence.IGridDAO;

public class GridDb4oDAO implements IGridDAO {

	private ObjectContainer db;

	public GridDb4oDAO() {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				"grid.data");
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
	public void deleteGridById(final UUID id) {
		db.delete(getGridById(id));
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
	public boolean containsGridById(final UUID id) {
		List<IGrid> grids = db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (grid.getId().equals(id));
			}
		});

		if (grids.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public IGrid getGridById(final UUID id) {
		List<IGrid> grids = db.query(new Predicate<IGrid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(IGrid grid) {
				return (id.equals(grid.getId()));
			}
		});

		if (grids.size() > 0) {
			return grids.get(0);
		}
		return null;
	}
}
