package business;

import models.CartEntity;
import models.ProductCart;
import models.CartProducts;

public class CartService {
	
	public CartEntity CreateCartEntity(CartProducts products, int id)
	{
		CartEntity cart = new CartEntity();
		cart.setId(id);
		
		String ids = "";
		String names = "";
		String counts = "";
		String prices = "";
		int count_Total=0;
		float price_Total = 0f;
		for(ProductCart pro : products.getProducts())
		{
			ids += Integer.toString(pro.getId());
			ids += ";";
			
			names += pro.getName();
			names += ";";
			
			counts += Integer.toString(pro.getCount());
			counts += ";";
			
			prices += Float.toString(pro.getPrice());
			prices += ";";
			
			count_Total += pro.getCount();
			price_Total += pro.getPrice();
		}
		cart.setCountTotal(count_Total);
		cart.setPriceTotal(price_Total);
		cart.setProductId(ids);
		cart.setProductName(names);
		cart.setProductCount(counts);
		cart.setProductPrice(prices);
		
		return cart;
	}
}
