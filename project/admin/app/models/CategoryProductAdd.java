package models;

public class CategoryProductAdd {
	private String name;
	private String category;
	public CategoryProductAdd() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryProductAdd(String name, String category) {
		super();
		this.name = name;
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
