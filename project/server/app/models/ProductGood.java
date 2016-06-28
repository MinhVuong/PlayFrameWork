package models;

public class ProductGood {
	private int id;
	private int count;
	public ProductGood() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductGood(int id, int count) {
		super();
		this.id = id;
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
