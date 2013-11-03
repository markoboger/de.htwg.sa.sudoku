package de.htwg.sudoku.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cell")
public class PersistentCell implements Serializable {

	private static final long serialVersionUID = 3184225396652683648L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	public Integer value = 0;

	@Column(name = "rowcell")
	public Integer row = 0;

	@Column(name = "columncell")
	public Integer column = 0;

	public Boolean given = false;

	@ManyToOne
	@JoinColumn(name = "gridid")
	public PersistentGrid grid;
	
	

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

	public PersistentGrid getGrid() {
		return grid;
	}

	public void setGrid(PersistentGrid grid) {
		this.grid = grid;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}
