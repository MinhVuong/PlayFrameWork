package pakageResult;

import java.util.List;

import entities.ProductEntity;

public class IndexPakage extends AbstractPakage{
	private List<ProductEntity> smartphones;
	private int pageSmartphone;
	private List<ProductEntity> laptop;
	private int pagelaptop;
	

	public IndexPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IndexPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	

	public int getPageSmartphone() {
		return pageSmartphone;
	}

	public void setPageSmartphone(int pageSmartphone) {
		this.pageSmartphone = pageSmartphone;
	}

	public List<ProductEntity> getLaptop() {
		return laptop;
	}

	public void setLaptop(List<ProductEntity> laptop) {
		this.laptop = laptop;
	}

	public int getPagelaptop() {
		return pagelaptop;
	}

	public void setPagelaptop(int pagelaptop) {
		this.pagelaptop = pagelaptop;
	}

	public IndexPakage(List<ProductEntity> smartphones, int pageSmartphone,
			List<ProductEntity> laptop, int pagelaptop) {
		super();
		this.smartphones = smartphones;
		this.pageSmartphone = pageSmartphone;
		this.laptop = laptop;
		this.pagelaptop = pagelaptop;
	}

	public List<ProductEntity> getSmartphones() {
		return smartphones;
	}

	public void setSmartphones(List<ProductEntity> smartphones) {
		this.smartphones = smartphones;
	}
	
}
