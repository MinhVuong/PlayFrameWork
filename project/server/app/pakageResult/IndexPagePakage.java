package pakageResult;

import java.util.List;

import entities.ProductEntity;

public class IndexPagePakage {
	private List<ProductEntity> products;

	
	public IndexPagePakage() {
		super();
		// TODO Auto-generated constructor stub
	}


	public IndexPagePakage(List<ProductEntity> products) {
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
