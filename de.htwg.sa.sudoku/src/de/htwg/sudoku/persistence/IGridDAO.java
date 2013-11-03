package de.htwg.sudoku.persistence;

import java.util.List;

import de.htwg.sudoku.model.IGrid;

public interface IGridDAO {
	
	void saveGrid(final IGrid grid);
	
	boolean containsGridById(final String id);
	
	List<IGrid> getGridsByDifficulty(final int max, final int min);
	
	IGrid getGridById(final String id);
	
	void deleteGridById(final String id);
	
	List<IGrid> getAllGrids();
	
	void generateGrids(final int number, int gridSize);
}