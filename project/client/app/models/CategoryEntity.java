package models;

public class CategoryEntity {

	private int id;
	private String name;
	private int numberRow;
	public CategoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryEntity(int id, String name, int num) {
		super();
		this.id = id;
		this.name = name;
		this.numberRow = num;
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
	public void setNumberRow(int num) {
		this.numberRow = num;
	}
	
}
