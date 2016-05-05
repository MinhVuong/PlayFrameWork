package models;

public class CartEdit {
	private String total;
	private String count;
	private String totalCart;

	public CartEdit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartEdit(String total, String count, String totalCart) {
		super();
		this.total = total;
		this.count = count;
		this.totalCart = totalCart;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTotalCart() {
		return totalCart;
	}

	public void setTotalCart(String totalCart) {
		this.totalCart = totalCart;
	}
	
}
