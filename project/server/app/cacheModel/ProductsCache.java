package cacheModel;

import java.util.List;

import entities.ProductEntity;

public class ProductsCache {
	private List<ProductEntity> products;

	public ProductsCache(List<ProductEntity> products) {
		super();
		this.products = products;
	}

	public ProductsCache() {
		super();
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	
}
