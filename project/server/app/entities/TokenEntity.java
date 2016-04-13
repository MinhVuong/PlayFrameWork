package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class TokenEntity {
	@Id
	@Column(name = "id_token")
	private int id;
	@Column(name = "customer_id")
	private int customer_id;
	@Column(name = "category_token_id")
	private int type;
	@Column(name = "token")
	private String token;
	@Column(name = "create_at")
	private String createAt;
	@Column(name = "expiry_date")
	private String expiryDate;
	public TokenEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TokenEntity(int id, int customer_id, int type, String token,
			String createAt, String expiryDate) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.type = type;
		this.token = token;
		this.createAt = createAt;
		this.expiryDate = expiryDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	

}
