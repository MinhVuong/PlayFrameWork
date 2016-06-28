package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.CheckOut;

@Entity
@Table(name = "orders")
public class OrderEntity {
	@Id
	@Column(name = "id_order")
	private int id;
	@Column(name = "status")
	private int status;
	@Column(name = "customer_id")
	private int customerId;
	@Column(name = "cart_id")
	private int cartId;
	@Column(name = "address_id")
	private int addressId;
	@Column(name = "create_at")
	private String createAt;
	@Column(name = "update_at")
	private String updateAt;
	@Column(name = "product_ids")
	private String ids;
	@Column(name = "product_names")
	private String names;
	@Column(name = "product_counts")
	private String counts;
	@Column(name = "product_prices")
	private String prices;
	@Column(name = "product_total_count")
	private int totalCount;
	@Column(name = "product_total_price")
	private float totalPrice;
	@Column(name = "key_sale_customer")
	private int keySaleCustomer;
	@Column(name = "value_sale_customer")
	private String valueSaleCustomer;
	@Column(name = "shipping_price")
	private float shipPrice;
	@Column(name = "payment_total_customer")
	private float payment;
	public OrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderEntity(int id, int status, int customerId, int cartId,
			int addressId, String createAt, String updateAt, String ids,
			String names, String counts, String prices, int totalCount,
			float totalPrice, int keySaleCustomer, String valueSaleCustomer,
			float shipPrice, float payment) {
		super();
		this.id = id;
		this.status = status;
		this.customerId = customerId;
		this.cartId = cartId;
		this.addressId = addressId;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.ids = ids;
		this.names = names;
		this.counts = counts;
		this.prices = prices;
		this.totalCount = totalCount;
		this.totalPrice = totalPrice;
		this.keySaleCustomer = keySaleCustomer;
		this.valueSaleCustomer = valueSaleCustomer;
		this.shipPrice = shipPrice;
		this.payment = payment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getKeySaleCustomer() {
		return keySaleCustomer;
	}
	public void setKeySaleCustomer(int keySaleCustomer) {
		this.keySaleCustomer = keySaleCustomer;
	}
	public String getValueSaleCustomer() {
		return valueSaleCustomer;
	}
	public void setValueSaleCustomer(String valueSaleCustomer) {
		this.valueSaleCustomer = valueSaleCustomer;
	}
	public float getShipPrice() {
		return shipPrice;
	}
	public void setShipPrice(float shipPrice) {
		this.shipPrice = shipPrice;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	
	public void ConvertFromCheckOut(CheckOut c, CartEntity ca){
		this.cartId = c.getCartId();
		this.customerId = c.getCustomerId();
		this.status = 1;
		
		this.ids = ca.getProductId();
		this.names = ca.getProductName();
		this.counts = ca.getProductCount();
		this.prices = ca.getProductPrice();
		this.totalCount = ca.getCountTotal();
		this.totalPrice = ca.getPriceTotal();
		int us = 22325;
		this.payment = this.totalPrice/us;
		
	}
	
}
