package pakageResult;

import java.util.List;

import entities.ProductEntity;

public class CategoryProduct2Pakage extends AbstractPakage {
	private int id;
	private String name;
	List<ProductEntity> products;
	public CategoryProduct2Pakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryProduct2Pakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public CategoryProduct2Pakage(int id, String name,
			List<ProductEntity> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
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
	public List<ProductEntity> getProducts() {
		return products;
	}
	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
	
}
