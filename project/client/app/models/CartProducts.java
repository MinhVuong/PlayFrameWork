package models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartProducts {
	private List<ProductCart> products;

	public CartProducts() {
		super();
		// TODO Auto-generated constructor stub
		products = new ArrayList<ProductCart>();
	}

	public CartProducts(List<ProductCart> products) {
		super();
		products = new ArrayList<ProductCart>();
		this.products = products;
	}
	public CartProducts(CartProducts cart)
	{
		products = new ArrayList<ProductCart>();
		this.products = cart.products;
	}
	
	public List<ProductCart> getProducts() {
		return products;
		
	}

	public void setProducts(List<ProductCart> products) {
		this.products = products;
	}
	
	
	public void AddProduct(ProductCart product)
	{
		if(this.products == null)
		{
			this.products = new ArrayList<ProductCart>();
			this.products.add(product);
			return;
		}
		
		if(CheckExistProduct(product))
		{
			IncreaseCountProduct(product);
		}
		else
		{
			this.products.add(product);
		}
		
		
	}
	
	public boolean CheckExistProduct(ProductCart product)
	{
		for(ProductCart pro : this.products)
		{
			if(pro.getId() == product.getId())
				return true;
		}
		return false;
	}
	
	public void IncreaseCountProduct(ProductCart product)
	{
		List<ProductCart> temp = this.products;
		int i=0;
		for(ProductCart pro : temp)
		{
			if(pro.getId() == product.getId())
			{
				int count = pro.getCount() + 1;
				temp.get(i).setCount(count);
			}
			i++;
		}
		this.products = temp;
	}
	
	public void AddCountProduct(int id)
	{
		List<ProductCart> temp = this.products;
		int i=0;
		for(ProductCart pro : temp)
		{
			if(pro.getId() == id)
			{
				int count = pro.getCount() + 1;
				temp.get(i).setCount(count);
			}
			i++;
		}
		this.products = temp;
	}
	public void SubCountProduct(int id)
	{
		List<ProductCart> temp = this.products;
		int i=0;
		for(ProductCart pro : temp)
		{
			if(pro.getId() == id)
			{
				int count = pro.getCount();
				if(count > 1)
					count = count - 1;
				temp.get(i).setCount(count);
			}
			i++;
		}
		this.products = temp;
	}
	public void DeleteCountProduct(int id)
	{
		List<ProductCart> temp = new ArrayList<ProductCart>();
		for(ProductCart pro : this.products)
		{
			if(pro.getId() != id)
			{
				temp.add(pro);
			}
		}
		this.products = new ArrayList<ProductCart>();
		this.products = temp;
	}
	
	public String GetProductsTotal()
	{
		int sum=0;
		for(ProductCart pro : this.products){
			sum += pro.getCount();
		}
		return Integer.toString(sum);
	}
	
	public static String priceWithDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (float price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}
	
	public String GetPricesTotal()
	{
		float sum=0;
		for(ProductCart pro : this.products){
			sum += pro.getCount() * pro.getPrice();
		}
		String toShow = priceWithoutDecimal(sum);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(sum);
	    } else {
	        return priceWithoutDecimal(sum);
	    }
		
	}
	
	public String GetTotalProduct(int id)
	{
		String total = "";
		for(ProductCart pro : this.products){
			if(pro.getId() == id)
				total = pro.GetTotal();
		}
		return total;
	}

	public int GetRowProduct(ProductCart item)
	{
		int row=1;
		for(ProductCart pro : this.products){
			if(pro.getId() == item.getId())
			{
				return row;
			}
			row++;
		}
		return row;
	}
}
