package models;

public class CheckOut {
	private int customerId;
	private int cartId;
	private int addressId;
	private Address address;
	public CheckOut() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CheckOut(int customerId, int cartId, Address address, int add) {
		super();
		this.customerId = customerId;
		this.cartId = cartId;
		this.address = address;
		this.addressId = add;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
}
