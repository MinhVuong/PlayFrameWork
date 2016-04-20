package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {
	@Id
	@Column(name = "id_product")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private float price;
	@Column(name = "is_new")
	private int isNew;
	@Column(name = "is_sale")
	private int isSale;
	@Column(name = "price_sale")
	private float priceSale;
	@Column(name = "is_wishlist")
	private int isWishlist;
	@Column(name = "image")
	private String image;
	@Column(name = "is_active")
	private int isActive;
	@Column(name = "category_product_id")
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
	
	
	
}
