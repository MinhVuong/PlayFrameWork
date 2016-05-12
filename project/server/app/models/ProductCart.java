package models;

import java.text.DecimalFormat;
import java.util.List;

import entities.ProductCountEntity;
import entities.ProductEntity;

public class ProductCart {
	private int id;
	private String name;
	private float price;
	private String image;
	private int count;
	private String color;
	List<ProductCountEntity> counts;
	
	public ProductCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductCart(int id, String name, float price, String image, int count, List<ProductCountEntity> counts, String color) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.count = count;
		this.counts = counts;
		this.color = color;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
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
	
	
	public List<ProductCountEntity> getCounts() {
		return counts;
	}
	public void setCounts(List<ProductCountEntity> counts) {
		this.counts = counts;
	}
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void ConvertFromProductEntity(ProductEntity product, List<ProductCountEntity> counts)
	{
		this.id = product.getId();
		this.name = product.getName();
		this.count = 1;
		this.image = product.getImage();
		this.counts = counts;
		
		ProductCountEntity pro = counts.get(0);
		this.color = pro.getKey();
		if(product.getIsSale() ==  1)
			this.price = product.getPriceSale();
		else
			this.price = product.getPrice();
	}
	
	public static String priceWithDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}
	
	public String priceToString() {
	    String toShow = priceWithoutDecimal(this.price);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(price);
	    } else {
	        return priceWithoutDecimal(price);
	    }
	}
	
	public String priceToString(float money) {
	    String toShow = priceWithoutDecimal(money);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(money);
	    } else {
	        return priceWithoutDecimal(money);
	    }
	}
}
