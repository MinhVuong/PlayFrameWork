package pakageResult;

import java.util.ArrayList;
import java.util.List;

import models.Category;
import entities.CategoryEntity;
import entities.CategoryProductEntity;

public class IndexFullPakage extends AbstractPakage{
	private IndexPakage indexPakage;
	private List<Category> categories;
	private List<List<CategoryProductEntity>> categoryProducts;
	public IndexFullPakage() {
		super();
		// TODO Auto-generated constructor stub
		categoryProducts = new ArrayList<List<CategoryProductEntity>>();
	}
	public IndexFullPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
		categoryProducts = new ArrayList<List<CategoryProductEntity>>();
	}
	public IndexFullPakage(IndexPakage indexPakage,
			List<Category> categories,
			List<List<CategoryProductEntity>> categoryProducts) {
		super();
		this.indexPakage = indexPakage;
		this.categories = categories;
		this.categoryProducts = categoryProducts;
	}
	public IndexPakage getIndexPakage() {
		return indexPakage;
	}
	public void setIndexPakage(IndexPakage indexPakage) {
		this.indexPakage = indexPakage;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<List<CategoryProductEntity>> getCategoryProducts() {
		return categoryProducts;
	}
	public void setCategoryProducts(List<List<CategoryProductEntity>> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}
	
	public void addCategoryProducts(List<CategoryProductEntity> categoryProducts) {
		this.categoryProducts.add(categoryProducts);
	}
	
	
}
