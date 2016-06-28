package pakageResult;

import java.util.List;

import models.ProductGood;
import entities.ProductEntity;

public class ProductGoodPakage extends AbstractPakage{
	private List<ProductEntity> products;

	public ProductGoodPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductGoodPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public ProductGoodPakage(List<ProductEntity> products) {
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
