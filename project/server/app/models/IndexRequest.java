package models;

public class IndexRequest {
	private int countSmartphone;
	private int pageSmartphone;
	private int countLaptop;
	private int pageLaptop;
	private int countTablet;
	private int pageTablet;
	public IndexRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IndexRequest(int countSmartphone, int pageSmartphone,
			int countLaptop, int pageLaptop, int countTablet, int pageTablet) {
		super();
		this.countSmartphone = countSmartphone;
		this.pageSmartphone = pageSmartphone;
		this.countLaptop = countLaptop;
		this.pageLaptop = pageLaptop;
		this.countTablet = countTablet;
		this.pageTablet = countTablet;
	}
	public int getCountSmartphone() {
		return countSmartphone;
	}
	public void setCountSmartphone(int countSmartphone) {
		this.countSmartphone = countSmartphone;
	}
	public int getPageSmartphone() {
		return pageSmartphone;
	}
	public void setPageSmartphone(int pageSmartphone) {
		this.pageSmartphone = pageSmartphone;
	}
	public int getCountLaptop() {
		return countLaptop;
	}
	public void setCountLaptop(int countLaptop) {
		this.countLaptop = countLaptop;
	}
	public int getPageLaptop() {
		return pageLaptop;
	}
	public void setPageLaptop(int pageLaptop) {
		this.pageLaptop = pageLaptop;
	}
	public int getCountTablet() {
		return countTablet;
	}
	public void setCountTablet(int countTablet) {
		this.countTablet = countTablet;
	}
	public int getPageTablet() {
		return pageTablet;
	}
	public void setPageTablet(int pageTablet) {
		this.pageTablet = pageTablet;
	}
	
	
}
