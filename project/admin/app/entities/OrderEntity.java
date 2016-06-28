package entities;

public class OrderEntity {
	private int id;
	private int status;
	private int customerId;
	private int cartId;
	private int addressId;
	private String createAt;
	private String updateAt;
	private String ids;
	private String names;
	private String prices;
	private String counts;
	private int totalCount;
	private float totalPrice;
	private int keySaleCustomer;
	private String valueSaleCustomer;
	private float shipPrice;
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
}
