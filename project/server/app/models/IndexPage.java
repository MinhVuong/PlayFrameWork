package models;

public class IndexPage {
	private int count;
	private int page;
	private int category;
	public IndexPage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IndexPage(int count, int page, int category) {
		super();
		this.count = count;
		this.page = page;
		this.category = category;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}	
