package models;

public class Category {
	private int id;
	private String name;
	private int numberRow;
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int id, String name, int numberRow) {
		super();
		this.id = id;
		this.name = name;
		this.numberRow = numberRow;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberRow() {
		return numberRow;
	}
	public void setNumberRow(int numberRow) {
		this.numberRow = numberRow;
	}
	
}
