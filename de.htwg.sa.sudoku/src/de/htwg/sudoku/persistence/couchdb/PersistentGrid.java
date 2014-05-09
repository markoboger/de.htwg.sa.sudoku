package de.htwg.sudoku.persistence.couchdb;

import java.util.Set;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class PersistentGrid extends CouchDbDocument {

	private static final long serialVersionUID = 1538704903825440126L;

	/**
	 * @TypeDiscriminator is used to mark properties that makes this class's
	 *                    documents unique in the database.
	 */
	@TypeDiscriminator
	private String id;

	private Set<PersistentCell> cells;

	private String name;
	private int blocksPerEdge;
	private int numberSetCells;

	public PersistentGrid() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<PersistentCell> getCells() {
		return cells;
	}

	public void setCells(Set<PersistentCell> cells) {
		this.cells = cells;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public int getBlocksPerEdge() {
		return blocksPerEdge;
	}

	public void setBlocksPerEdge(int blocksPerEdge) {
		this.blocksPerEdge = blocksPerEdge;
	}

	public int getNumberSetCells() {
		return numberSetCells;
	}

	public void setNumberSetCells(int numberSetCells) {
		this.numberSetCells = numberSetCells;
	}
}
