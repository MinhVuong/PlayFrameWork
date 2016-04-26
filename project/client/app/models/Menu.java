package models;

import java.util.List;

public class Menu {
	List<CategoryEntity> categories;
	List<List<CategoryProductEntity>> categoryProducts;
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Menu(List<CategoryEntity> categories,
			List<List<CategoryProductEntity>> categoryProducts) {
		super();
		this.categories = categories;
		this.categoryProducts = categoryProducts;
	}
	public List<CategoryEntity> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}
	public List<List<CategoryProductEntity>> getCategoryProducts() {
		return categoryProducts;
	}
	public void setCategoryProducts(
			List<List<CategoryProductEntity>> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}
	
	
}
