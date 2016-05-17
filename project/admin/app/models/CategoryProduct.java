package models;

public class CategoryProduct {
	private int id;
	private String name;
	private int idCate;
	private String cate;
	public CategoryProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryProduct(int id, String name, String cate, int idC) {
		super();
		this.id = id;
		this.name = name;
		this.cate = cate;
		this.idCate = idC;
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
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public int getIdCate() {
		return idCate;
	}
	public void setIdCate(int idCate) {
		this.idCate = idCate;
	}
	
}
