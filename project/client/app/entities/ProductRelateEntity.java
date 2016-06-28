package entities;

public class ProductRelateEntity {
	private int id;
	private int productId;
	private int productRelate;
	public ProductRelateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductRelateEntity(int id, int productId, int productRelate) {
		super();
		this.id = id;
		this.productId = productId;
		this.productRelate = productRelate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductRelate() {
		return productRelate;
	}
	public void setProductRelate(int productRelate) {
		this.productRelate = productRelate;
	}
}
