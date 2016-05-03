package business;
import models.CartProducts;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Http.Session;

import com.fasterxml.jackson.databind.JsonNode;

public class SessionManager {
	
	public CartProducts GetCartProducts()
	{
		CartProducts products = new CartProducts();
		Session sessionM = Http.Context.current().session();
		String productsS = sessionM.get("products");
		if(productsS != null)
		{
			JsonNode json = Json.parse(productsS);
			products = Json.fromJson(json, CartProducts.class);
		}
		return products;
	}
	
	public void AddCountProduct(int id)
	{
		CartProducts products = new CartProducts();
		Session sessionM = Http.Context.current().session();
		String productsS = sessionM.get("products");
		if(productsS != null)
		{
			JsonNode json = Json.parse(productsS);
			products = Json.fromJson(json, CartProducts.class);
			products.AddCountProduct(id);
			sessionM.put("products", Json.toJson(products).toString());
		}
	}
	
	public void SubCountProduct(int id)
	{
		CartProducts products = new CartProducts();
		Session sessionM = Http.Context.current().session();
		String productsS = sessionM.get("products");
		if(productsS != null)
		{
			JsonNode json = Json.parse(productsS);
			products = Json.fromJson(json, CartProducts.class);
			products.SubCountProduct(id);
			sessionM.put("products", Json.toJson(products).toString());
		}
	}
	
	
}
