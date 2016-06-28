package pakageResultAdmin;
import java.util.List;

import entities.ProductCountEntity;
import models.ProductCountCate;
import models.ProductCountShow;
import pakageResult.AbstractPakage;

public class ProductCountAdminPakage extends AbstractPakage{
	private List<ProductCountEntity> colors;
	private List<ProductCountCate> cates;
	public ProductCountAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductCountAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	

	public ProductCountAdminPakage(List<ProductCountEntity> colors,
			List<ProductCountCate> cates) {
		super();
		this.colors = colors;
		this.cates = cates;
	}

	public List<ProductCountEntity> getColors() {
		return colors;
	}

	public void setColors(List<ProductCountEntity> colors) {
		this.colors = colors;
	}

	public List<ProductCountCate> getCates() {
		return cates;
	}

	public void setCates(List<ProductCountCate> cates) {
		this.cates = cates;
	}
	
}
