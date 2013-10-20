package de.htwg.sudoku.persistence;

import java.util.List;
import java.util.UUID;

import de.htwg.sudoku.model.IGrid;

public interface IGridDAO {
	
	void saveGrid(final IGrid grid);
	
	boolean containsGridById(final UUID id);
	
	List<IGrid> getGridsByDifficulty(final int max, final int min);
	
	IGrid getGridById(final UUID id);
	
	void deleteGridById(final UUID id);
	
	List<IGrid> getAllGrids();
	
	void generateGrids(final int number, int gridSize);
}