package pakageResultAdmin;
import java.util.List;

import models.ProductCountCate;
import models.ProductCountShow;
import pakageResult.AbstractPakage;

public class ProductCountAdminPakage extends AbstractPakage{
	private List<ProductCountShow> colors;
	private List<ProductCountCate> cates;
	public ProductCountAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductCountAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	

	public ProductCountAdminPakage(List<ProductCountShow> colors,
			List<ProductCountCate> cates) {
		super();
		this.colors = colors;
		this.cates = cates;
	}

	public List<ProductCountShow> getColors() {
		return colors;
	}

	public void setColors(List<ProductCountShow> colors) {
		this.colors = colors;
	}

	public List<ProductCountCate> getCates() {
		return cates;
	}

	public void setCates(List<ProductCountCate> cates) {
		this.cates = cates;
	}
	
}
