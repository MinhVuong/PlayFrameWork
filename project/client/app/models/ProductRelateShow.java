package models;

public class ProductRelateShow {
	private ProductEntity product1;
	private ProductEntity product2;
	private ProductEntity product3;
	private int tag;
	public ProductRelateShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductRelateShow(ProductEntity product1, ProductEntity product2,
			ProductEntity product3, int tag) {
		super();
		this.product1 = product1;
		this.product2 = product2;
		this.product3 = product3;
		this.tag = tag;
	}
	public ProductEntity getProduct1() {
		return product1;
	}
	public void setProduct1(ProductEntity product1) {
		this.product1 = product1;
	}
	public ProductEntity getProduct2() {
		return product2;
	}
	public void setProduct2(ProductEntity product2) {
		this.product2 = product2;
	}
	public ProductEntity getProduct3() {
		return product3;
	}
	public void setProduct3(ProductEntity product3) {
		this.product3 = product3;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
