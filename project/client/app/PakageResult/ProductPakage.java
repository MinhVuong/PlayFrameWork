package PakageResult;

import models.ProductCart;

public class ProductPakage extends AbstractPakage{
	private ProductCart product;

	public ProductPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public ProductPakage(ProductCart product) {
		super();
		this.product = product;
	}

	public ProductCart getProduct() {
		return product;
	}

	public void setProduct(ProductCart product) {
		this.product = product;
	}
	
}
