package models;
import java.util.List;

import entities.CategoryEntity;
public class Categories {
	List<CategoryEntity> categories;

	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categories(List<CategoryEntity> categories) {
		super();
		this.categories = categories;
	}

	public List<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}
	
}
