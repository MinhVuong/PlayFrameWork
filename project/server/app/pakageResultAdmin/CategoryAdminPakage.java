package pakageResultAdmin;

import java.util.List;

import models.Categories;
import entities.CategoryEntity;
import pakageResult.AbstractPakage;

public class CategoryAdminPakage extends AbstractPakage {
	List<CategoryEntity> category;

	public CategoryAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public CategoryAdminPakage(List<CategoryEntity> category) {
		super();
		this.category = category;
	}

	public List<CategoryEntity> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryEntity> category) {
		this.category = category;
	}

	

	

	
	
}
