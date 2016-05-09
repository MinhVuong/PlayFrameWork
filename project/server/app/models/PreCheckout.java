package models;

import java.util.List;

public class PreCheckout {
	private int id;
	private CartProducts products;
	public PreCheckout() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PreCheckout(int id, CartProducts products) {
		super();
		this.id = id;
		this.products = products;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CartProducts getProducts() {
		return products;
	}
	public void setProducts(CartProducts products) {
		this.products = products;
	}
}
