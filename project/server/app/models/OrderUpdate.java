package models;

public class OrderUpdate {
	private int id;
	private int status;
	public OrderUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderUpdate(int id, int status) {
		super();
		this.id = id;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
