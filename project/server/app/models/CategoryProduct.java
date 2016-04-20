package models;

public class CategoryProduct {
	private int id;
	private String name;
	private int idCategory;
	public CategoryProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryProduct(int id, String name, int idCategory) {
		super();
		this.id = id;
		this.name = name;
		this.idCategory = idCategory;
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
	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	
	
}
