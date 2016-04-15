package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_address")
public class AddressEntity {
	@Id
	@Column(name = "id_customer_address")
	private int id;
	@Column(name = "customer_id")
	private int customer_id;
	@Column(name = "create_at")
	private String createAt;
	@Column(name = "update_at")
	private String updateAt;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "phone")
	private String phone;
	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;
	public AddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddressEntity(int id, int customer_id, String createAt,
			String updateAt, String firstName, String lastName, String phone,
			String street, String city) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.street = street;
		this.city = city;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	

}
