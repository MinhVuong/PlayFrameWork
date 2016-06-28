package PakageResult;

import java.util.List;

import models.ProductEntity;
import entities.ProductImageEntity;
import entities.ProductInforEntity;

public class ProductDetailPakage extends AbstractPakage{
	private ProductEntity product;
	private List<ProductInforEntity> infors;
	private List<ProductImageEntity> images;
	private List<ProductEntity> relates;
	public ProductDetailPakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductDetailPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	public ProductDetailPakage(ProductEntity product,
			List<ProductInforEntity> infors, List<ProductImageEntity> images,
			List<ProductEntity> relates) {
		super();
		this.product = product;
		this.infors = infors;
		this.images = images;
		this.relates = relates;
	}
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	public List<ProductInforEntity> getInfors() {
		return infors;
	}
	public void setInfors(List<ProductInforEntity> infors) {
		this.infors = infors;
	}
	public List<ProductImageEntity> getImages() {
		return images;
	}
	public void setImages(List<ProductImageEntity> images) {
		this.images = images;
	}
	public List<ProductEntity> getRelates() {
		return relates;
	}
	public void setRelates(List<ProductEntity> relates) {
		this.relates = relates;
	}
	

}
