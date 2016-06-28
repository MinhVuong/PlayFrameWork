package models;

import java.text.DecimalFormat;

import entities.OrderEntity;

public class OrderShow {
	private int id;
	private int status;
	private String name;
	private int totalCount;
	private String totalPrice;
	private String ids;
	private String names;
	private String counts;
	private String prices;
	private float payment;
	public OrderShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderShow(int id, int status, String name, int totalCount,
			String totalPrice, String ids, String names, String counts,
			String prices, float payment) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.totalCount = totalCount;
		this.totalPrice = totalPrice;
		this.ids = ids;
		this.names = names;
		this.counts = counts;
		this.prices = prices;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	public static String priceWithDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}
	public String priceToString(float f) {
	    String toShow = priceWithoutDecimal(f);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(f);
	    } else {
	        return priceWithoutDecimal(f);
	    }
	}
	public void ConvertFormOrderEntityAndCustomerEntity(OrderEntity en, String name){
		this.id = en.getId();
		this.status = en.getStatus();
		this.name = name;
		this.totalCount = en.getTotalCount();
		this.totalPrice = priceToString(en.getTotalPrice());
		this.ids = en.getIds();
		this.names = en.getNames();
		this.counts = en.getCounts();
		this.prices = en.getPrices();
		this.payment = en.getPayment();
	}
	
}
