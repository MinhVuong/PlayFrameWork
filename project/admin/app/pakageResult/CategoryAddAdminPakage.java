package pakageResult;

import java.util.List;

import entities.CategoryEntity;

public class CategoryAddAdminPakage extends AbstractPakage{
	List<CategoryEntity> categories;
	
	public CategoryAddAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryAddAdminPakage(List<CategoryEntity> categories) {
		super();
		this.categories = categories;
	}

	public CategoryAddAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public List<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}

}
