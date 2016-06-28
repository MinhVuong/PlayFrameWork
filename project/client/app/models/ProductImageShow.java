package models;

import entities.ProductImageEntity;

public class ProductImageShow {
	private int id;
	private int productId;
	private String path;
	private int tag;
	public ProductImageShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductImageShow(int id, int productId, String path, int tag) {
		super();
		this.id = id;
		this.productId = productId;
		this.path = path;
		this.tag = tag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	public void ConvertFromProductImageEntity(ProductImageEntity en){
		this.id = en.getId();
		this.path = en.getPath();
		this.productId = en.getProductId();
		this.tag = 0;
	}
	
}
