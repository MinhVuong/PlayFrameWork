package models;

public class ProductEntityShow {
	private int id;
	private String name;
	private String image;
	private int isNew;
	private int isActive;
	private String salePrice;
	private int isWishList;
	private int idCate;
	private String categoryP;
	public ProductEntityShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductEntityShow(int id, String name, String image, int isNew, int active,
			String salePrice, int isWishList, String categoryP, int idcate) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.isNew = isNew;
		this.isActive = active;
		this.salePrice = salePrice;
		this.isWishList = isWishList;
		this.categoryP = categoryP;
		this.idCate = idcate;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public int getIsWishList() {
		return isWishList;
	}
	public void setIsWishList(int isWishList) {
		this.isWishList = isWishList;
	}
	public String getCategoryP() {
		return categoryP;
	}
	public void setCategoryP(String categoryP) {
		this.categoryP = categoryP;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getIdCate() {
		return idCate;
	}
	public void setIdCate(int idCate) {
		this.idCate = idCate;
	}
	
	
}
