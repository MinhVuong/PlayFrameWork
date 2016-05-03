package models;

import entities.ProductEntity;

public class ProductCart {
	private int id;
	private String name;
	private float price;
	private String image;
	private int count;
	public ProductCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCart(int id, String name, float price, String image, int count) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	public void ConvertFromProductEntity(ProductEntity product)
	{
		this.id = product.getId();
		this.name = product.getName();
		this.count = 1;
		this.image = product.getImage();
		if(product.getIsSale() ==  1)
			this.price = product.getPriceSale();
		else
			this.price = product.getPrice();
	}
}
