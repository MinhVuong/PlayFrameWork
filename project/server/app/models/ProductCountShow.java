package models;

import javax.persistence.Column;

import entities.ProductCountEntity;

public class ProductCountShow {
	private int id;
	private String name;
	private String key;
	private String value;
	private float price;
	private int count;
	public ProductCountShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCountShow(int id, String name, String key, String value,
			float price, int count) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.value = value;
		this.price = price;
		this.count = count;
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
	
	
	public void ConvertFromProductCountEntity(ProductCountEntity e, String name){
		this.id = e.getId();
		this.count = e.getCount();
		this.key = e.getKey();
		this.price = e.getPrice();
		this.value = e.getValue();
		this.name = name;
	}
}
