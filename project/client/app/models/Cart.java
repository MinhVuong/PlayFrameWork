package models;

public class Cart {
	private int id;			// id product
	private String name;
	private String image;
	private int count;
	private String price;
	private String total;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cart(int id, String name, String image, int count, String price,
			String total) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.count = count;
		this.price = price;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
