package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_pictues")
public class ProductImageEntity {
	@Id
	@Column(name = "id_product_picture")
	private int id;
	@Column(name = "product_id")
	private int productId;
	@Column(name = "path")
	private String path;
	public ProductImageEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductImageEntity(int id, int productId, String path) {
		super();
		this.id = id;
		this.productId = productId;
		this.path = path;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
