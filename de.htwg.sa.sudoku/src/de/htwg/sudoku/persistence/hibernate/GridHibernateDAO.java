package de.htwg.sudoku.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.htwg.sudoku.model.ICell;
import de.htwg.sudoku.model.IGrid;
import de.htwg.sudoku.model.impl.Grid;
import de.htwg.sudoku.persistence.IGridDAO;

public class GridHibernateDAO implements IGridDAO {

	private IGrid copyGrid(PersistentGrid pgrid) {
		if(pgrid == null) {
			return null;
		}
		IGrid grid = new Grid(pgrid.getBlocksPerEdge());
		grid.setId(pgrid.getId());
		grid.setName(pgrid.getName());

		for (PersistentCell cellBase : pgrid.getCells()) {
			int column = cellBase.getColumn();
			int row = cellBase.getRow();
			int value = cellBase.getValue();
			boolean given = cellBase.getGiven();

			ICell cell = grid.getICell(column, row);
			cell.setValue(value);
			cell.setGiven(given);
		}

		return grid;
	}

	private PersistentGrid copyGrid(IGrid grid) {
		if(grid == null) {
			return null;
		}
		
		String gridId = grid.getId();
		PersistentGrid pgrid;
		if(containsGridById(gridId)) {
			// The Object already exists within the session
			Session session = HibernateUtil.getInstance().getCurrentSession();
			pgrid = (PersistentGrid) session.get(PersistentGrid.class, gridId);
			
			// Synchronize values			
			List<PersistentCell> cells = pgrid.getCells();
			for(PersistentCell c : cells) {
				int col = c.getColumn();
				int row = c.getRow();
				c.setValue(grid.getICell(col, row).getValue());
			}
			
		} else {
			// A new database entry
			pgrid = new PersistentGrid();
			
			List<PersistentCell> cells = new ArrayList<PersistentCell>();

			for (int column = 0; column < Math.pow(grid.getBlockSize(), 2); column++) {
				for (int row = 0; row < Math.pow(grid.getBlockSize(), 2); row++) {
					ICell cell = grid.getICell(column, row);
					
					PersistentCell pcell = new PersistentCell(column, row);
					pcell.setGiven(cell.isGiven());
					pcell.setValue(cell.getValue());

					pcell.setGrid(pgrid);
					cells.add(pcell);
				}
			}
			pgrid.setCells(cells);
		}
		
		pgrid.setId(grid.getId());
		pgrid.setName(grid.getName());
		pgrid.setBlocksPerEdge(grid.getBlockSize());
		pgrid.setNumberSetCells(grid.getNumberSetCells());

		return pgrid;
	}

	@Override
	public void saveGrid(IGrid grid) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentGrid pgrid = copyGrid(grid);
			
			session.saveOrUpdate(pgrid);
			for (PersistentCell pcell : pgrid.getCells()) {
				session.saveOrUpdate(pcell);
			}

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RuntimeException(ex.getMessage());

		}

	}

	@Override
	public boolean containsGridById(String id) {
		if(getGridById(id) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<IGrid> getGridsByDifficulty(int max, int min) {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PersistentGrid.class);
		
		@SuppressWarnings("unchecked")
		List<PersistentGrid> results = criteria.list();

		List<IGrid> grids = new ArrayList<IGrid>();
		for (PersistentGrid pgrid : results) {
			IGrid grid = copyGrid(pgrid);
			int count = grid.getNumberSetCells();
			if (count >= min && count <= max) {
				grids.add(grid);
			}
		}
		return grids;
	}

	@Override
	public IGrid getGridById(String id) {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		
		return copyGrid((PersistentGrid) session.get(PersistentGrid.class, id));
	}

	@Override
	public void deleteGridById(String id) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentGrid pgrid = (PersistentGrid) session.get(PersistentGrid.class, id);
			
			for(PersistentCell c : pgrid.getCells()) {
				session.delete(c);
			}
			session.delete(pgrid);

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
		    }
		}
	}

	@Override
	public List<IGrid> getAllGrids() {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PersistentGrid.class);
		
		@SuppressWarnings("unchecked")
		List<PersistentGrid> results = criteria.list();

		List<IGrid> grids = new ArrayList<IGrid>();
		for (PersistentGrid pgrid : results) {
			IGrid grid = copyGrid(pgrid);
			grids.add(grid);
		}
		return grids;
	}

	@Override
	public void generateGrids(int number, int gridSize) {
		for (int i = 0; i < number; i++) {
			IGrid grid = new Grid(gridSize);
			grid.create();
			saveGrid(grid);
		}
	}
}
