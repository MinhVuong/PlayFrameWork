package models;
import java.util.List;

import entities.ProductEntity;

public class ListProduct {
	private int id;
	private String name;
	List<ProductEntity> products;
	public ListProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListProduct(int id, String name, List<ProductEntity> products) {
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
