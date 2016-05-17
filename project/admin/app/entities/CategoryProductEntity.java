package entities;

public class CategoryProductEntity {
	private int id;
	private String name;
	private int idCategory;
	

	public CategoryProductEntity(int id, String name, int idCategory) {
		super();
		this.id = id;
		this.name = name;
		this.idCategory = idCategory;
	}
	public CategoryProductEntity() {
		super();
		// TODO Auto-generated constructor stub
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
