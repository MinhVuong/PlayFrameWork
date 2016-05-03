package models;

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
				if(count > 0)
					count = count - 1;
				temp.get(i).setCount(count);
			}
			i++;
		}
		this.products = temp;
	}
	
	
}
