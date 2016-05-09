package models;

public class CartEntity {
	private int id;
	private int customerId;
	private String createAt;
	private String updateAt;
	private int countTotal;
	private float priceTotal;
	private String productId;
	private String productName;
	private String productCount;
	private String productPrice;
	public CartEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartEntity(int id, int customerId, String createAt, String updateAt,
			int countTotal, float priceTotal, String productId,
			String productName, String productCount, String productPrice) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.countTotal = countTotal;
		this.priceTotal = priceTotal;
		this.productId = productId;
		this.productName = productName;
		this.productCount = productCount;
		this.productPrice = productPrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	public int getCountTotal() {
		return countTotal;
	}
	public void setCountTotal(int countTotal) {
		this.countTotal = countTotal;
	}
	public float getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(float priceTotal) {
		this.priceTotal = priceTotal;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
	
}
