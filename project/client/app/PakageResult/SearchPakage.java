package PakageResult;

import java.util.List;

import models.ProductEntity;

public class SearchPakage {
	private List<ProductEntity> products;

	public SearchPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchPakage(List<ProductEntity> products) {
		super();
		this.products = products;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
}
