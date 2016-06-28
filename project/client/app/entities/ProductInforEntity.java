package entities;

public class ProductInforEntity {
	private int id;
	private int productId;
	private String name;
	private String value;
	public ProductInforEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductInforEntity(int id, int productId, String name, String value) {
		super();
		this.id = id;
		this.productId = productId;
		this.name = name;
		this.value = value;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
