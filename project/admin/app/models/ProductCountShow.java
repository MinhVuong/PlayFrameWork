package models;

import java.text.DecimalFormat;

import entities.ProductCountEntity;

public class ProductCountShow {
	private int id;
	private int id_cate;
	private String name;
	private String key;
	private String value;
	private String price_S;
	private float price;
	private int count;
	public ProductCountShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCountShow(int id, int id_cate, String name, String key, String value,
			float price, String pri, int count) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.value = value;
		this.price = price;
		this.count = count;
		this.id_cate = id_cate;
		this.price_S = pri;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getId_cate() {
		return id_cate;
	}
	public void setId_cate(int id_cate) {
		this.id_cate = id_cate;
	}
	public static String priceWithDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}
	
	public String priceToString() {
	    
		String toShow = priceWithoutDecimal(this.price);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(price);
	    } else {
	        return priceWithoutDecimal(price);
	    }
	    
	}
	
	public String getPrice_S() {
		return price_S;
	}
	public void setPrice_S(String price_S) {
		this.price_S = price_S;
	}
	
	
	public String PriceEdit(){
		String temp;
		String toShow = priceWithoutDecimal(this.price);
	    if (toShow.indexOf(".") > 0) {
	        temp =  priceWithDecimal(price);
	    } else {
	        temp = priceWithoutDecimal(price);
	    }
	    int size = temp.length();
	    String result="";
	    for(int i=0; i< size; i++){
	    	char c = temp.charAt(i);
	    	if(c != ','){
	    		result += c;
	    	}
	    }
	    
		return result;
	}
	public void ConvertFromProductCountEntity(ProductCountEntity e,int id, String name){
		this.id = e.getId();
		this.count = e.getCount();
		this.key = e.getKey();
		this.price = e.getPrice();
		this.value = e.getValue();
		this.name = name;
		this.id_cate = id;
	}
}
