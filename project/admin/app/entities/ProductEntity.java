package entities;

import models.ProductEntityShow;

public class ProductEntity {
	private int id;
	private String name;
	private float price;
	private int isNew;
	private int isSale;
	private float priceSale;
	private int isWishlist;
	private String image;
	private int isActive;
	private int categoryId;
	public ProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductEntity(int id, String name, float price, int isNew,
			int isSale, float priceSale, int isWishlist, String image,
			int isActive, int categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.isNew = isNew;
		this.isSale = isSale;
		this.priceSale = priceSale;
		this.isWishlist = isWishlist;
		this.image = image;
		this.isActive = isActive;
		this.categoryId = categoryId;
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
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public int getIsSale() {
		return isSale;
	}
	public void setIsSale(int isSale) {
		this.isSale = isSale;
	}
	public float getPriceSale() {
		return priceSale;
	}
	public void setPriceSale(float priceSale) {
		this.priceSale = priceSale;
	}
	public int getIsWishlist() {
		return isWishlist;
	}
	public void setIsWishlist(int isWishlist) {
		this.isWishlist = isWishlist;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public void ConvertByProductShow(ProductEntityShow p){
		this.id = p.getId();
		this.categoryId = Integer.parseInt(p.getCategoryP());
		this.image = p.getImage();
		this.isActive = p.getIsActive();
		this.isNew = p.getIsNew();
		this.isWishlist = p.getIsWishList();
		this.name = p.getName();
		if(p.getSalePrice().equals(""))
			this.priceSale = 0f;
		else
			this.priceSale = Float.parseFloat(p.getSalePrice());
		this.price = 0f;
	}
}
