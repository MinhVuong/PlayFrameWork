package entities;

public class ProductCountEntity {
	private int id;
	private int productId;
	private String key;
	private String value;
	private float price;
	private int count;
	private int stockId;
	public ProductCountEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCountEntity(int id, int productId, String key, String value,
			float price, int count, int stockId) {
		super();
		this.id = id;
		this.productId = productId;
		this.key = key;
		this.value = value;
		this.price = price;
		this.count = count;
		this.stockId = stockId;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
}
