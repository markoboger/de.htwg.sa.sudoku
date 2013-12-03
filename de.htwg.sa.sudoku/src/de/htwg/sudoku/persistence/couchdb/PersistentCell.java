package de.htwg.sudoku.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class PersistentCell extends CouchDbDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6049548436571017626L;

	public String id;

	public Integer value = 0;
	public Integer row = 0;
	public Integer column = 0;

	public Boolean given = false;

	public PersistentCell(Integer column, Integer row) {
		this.column = column;
		this.row = row;
	}

	public PersistentCell() {
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getRowcell() {
		return row;
	}

	public void setRowcell(Integer rowcell) {
		this.row = rowcell;
	}

	public Integer getColumncell() {
		return column;
	}

	public void setColumncell(Integer columncell) {
		this.column = columncell;
	}

	public Boolean getGiven() {
		return given;
	}

	public void setGiven(Boolean given) {
		this.given = given;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
