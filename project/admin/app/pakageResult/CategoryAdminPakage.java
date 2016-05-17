package pakageResult;
import java.util.List;

import entities.CategoryEntity;


public class CategoryAdminPakage extends AbstractPakage{
	List<CategoryEntity> category;

	public CategoryAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public CategoryAdminPakage(List<CategoryEntity> categories) {
		super();
		this.category = categories;
	}

	public List<CategoryEntity> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryEntity> category) {
		this.category = category;
	}

	
}
