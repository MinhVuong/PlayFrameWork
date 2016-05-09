package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_colors_stock")
public class ProductCountEntity {
	@Id
	@Column(name = "id_product_color_stock")
	private int id;
	@Column(name = "product_id")
	private int productId;
	@Column(name = "key_color")
	private String key;
	@Column(name = "value_color")
	private String value;
	@Column(name = "price")
	private float price;
	@Column(name = "count")
	private int count;
	@Column(name = "stock_id")
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
