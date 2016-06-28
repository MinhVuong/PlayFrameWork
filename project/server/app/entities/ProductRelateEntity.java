package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_relation")
public class ProductRelateEntity {
	@Id
	@Column(name = "id_product_relation")
	private int id;
	@Column(name = "product_id")
	private int productId;
	@Column(name = "product_id_relation")
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
