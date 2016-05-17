package pakageResultAdmin;

import java.util.List;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import pakageResult.AbstractPakage;

public class CategoryProductAdminPakage  extends AbstractPakage{
	List<CategoryProductEntity> categoryP;
	List<CategoryEntity> categories;

	public CategoryProductAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryProductAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	
	public CategoryProductAdminPakage(List<CategoryProductEntity> categoryP,
			List<CategoryEntity> categories) {
		super();
		this.categoryP = categoryP;
		this.categories = categories;
	}

	public List<CategoryProductEntity> getCategoryP() {
		return categoryP;
	}

	public void setCategoryP(List<CategoryProductEntity> categoryP) {
		this.categoryP = categoryP;
	}

	public List<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}
	
	
	
}
