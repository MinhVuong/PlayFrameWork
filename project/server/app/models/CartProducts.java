package models;

import java.util.ArrayList;
import java.util.List;

public class CartProducts {
	private List<ProductCart> products;

	public CartProducts() {
		super();
		// TODO Auto-generated constructor stub
		products = new ArrayList<ProductCart>();
	}

	public CartProducts(List<ProductCart> products) {
		super();
		products = new ArrayList<ProductCart>();
		this.products = products;
	}

	public List<ProductCart> getProducts() {
		return products;
	}

	public void setProducts(List<ProductCart> products) {
		this.products = products;
	}
	
	
}
