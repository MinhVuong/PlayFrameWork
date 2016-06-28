package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_information")
public class ProductInforEntity {
	@Id
	@Column(name = "id_product_information")
	private int id;
	@Column(name = "product_id")
	private int productId;
	@Column(name = "name")
	private String name;
	@Column(name = "value")
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
