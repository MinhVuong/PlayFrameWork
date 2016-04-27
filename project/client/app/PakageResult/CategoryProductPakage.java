package PakageResult;

import java.util.List;

import models.CategoryProduct;
import models.ProductEntity;

public class CategoryProductPakage extends AbstractPakage {
	private List<CategoryProduct> categoryProducts;
	private List<List<ProductEntity>> productss;
	private String name;
	public CategoryProductPakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryProductPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public CategoryProductPakage(List<CategoryProduct> categoryProducts,
			List<List<ProductEntity>> productss, String name) {
		super();
		this.categoryProducts = categoryProducts;
		this.productss = productss;
		this.name = name;
	}
	public List<CategoryProduct> getCategoryProducts() {
		return categoryProducts;
	}
	public void setCategoryProducts(List<CategoryProduct> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}
	public List<List<ProductEntity>> getProductss() {
		return productss;
	}
	public void setProductss(List<List<ProductEntity>> productss) {
		this.productss = productss;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
