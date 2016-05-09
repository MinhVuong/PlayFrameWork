package models;

import java.text.DecimalFormat;
import java.util.List;

import entities.ProductCountEntity;

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
	
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<ProductCountEntity> getCounts() {
		return counts;
	}
	public void setCounts(List<ProductCountEntity> counts) {
		this.counts = counts;
	}
	public static String priceWithDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}
	public String PriceToString() {
	    String toShow = priceWithoutDecimal(this.price);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(price);
	    } else {
	        return priceWithoutDecimal(price);
	    }
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
	
	public String GetPriceByColor(String color)
	{
		float money = 0f;
		for(ProductCountEntity pro : this.counts)
		{
			if(pro.getKey().equals(color))
			{
				money = pro.getPrice();
				this.color = color;
			}
		}
		String toShow = priceWithoutDecimal(money);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(money);
	    } else {
	        return priceWithoutDecimal(money);
	    }
	}
	public String GetPriceByDefault()
	{
		
		ProductCountEntity pro = this.counts.get(0);
		
		String toShow = priceWithoutDecimal(pro.getPrice());
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(pro.getPrice());
	    } else {
	        return priceWithoutDecimal(pro.getPrice());
	    }
	}
	
	public void ConvertFromProductEntity(ProductEntity product, List<ProductCountEntity> counts)
	{
		this.id = product.getId();
		this.name = product.getName();
		this.count = 1;
		this.image = product.getImage();
		this.counts = counts;
		if(product.getIsSale() ==  1)
			this.price = product.getPriceSale();
		else
			this.price = product.getPrice();
	}

	public String GetTotal()
	{
		float total = 0f;
		ProductCountEntity pro = this.counts.get(0);
		total = pro.getPrice() * this.count;
		
		String toShow = priceWithoutDecimal(total);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(total);
	    } else {
	        return priceWithoutDecimal(total);
	    }
	}
	
	public String GetTotal(String color)
	{
		float total = 0f;
		for(ProductCountEntity pro: this.counts){
			if(pro.getKey().equals(color))
				total = pro.getPrice();
		}
		total = total * this.count;
		
		String toShow = priceWithoutDecimal(total);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(total);
	    } else {
	        return priceWithoutDecimal(total);
	    }
	}
	
	public float GetPriceUS()
	{
		float result = 30.5f;
		int us = 22325;
		result = (price*count)/us;
		result = (float)Math.round(result*10)/10; 
		return result;
	}
	
}
