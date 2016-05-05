package business;
import models.CartProducts;
import models.Menu;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Http.Session;
import PakageResult.User;

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
	public void DeleteCountProduct(int id)
	{
		CartProducts products = new CartProducts();
		Session sessionM = Http.Context.current().session();
		String productsS = sessionM.get("products");
		if(productsS != null)
		{
			JsonNode json = Json.parse(productsS);
			products = Json.fromJson(json, CartProducts.class);
			products.DeleteCountProduct(id);
			sessionM.put("products", Json.toJson(products).toString());
		}
	}
	
	
	public User GetUser()
	{
		User user = new User();
		Session sessionM = Http.Context.current().session();
		String userS = sessionM.get("user");
		if(userS != null)
		{
			JsonNode userN = Json.parse(userS);
			user = (User) Json.fromJson(userN, User.class);
		}
		return user;
	}
	
	public Menu GetMenu()
	{
		Menu menu = new Menu();
		Session sessionM = Http.Context.current().session();
		String menuS = sessionM.get("menu");
		if(menuS != null)
		{
			JsonNode menuN = Json.parse(menuS);
			menu = (Menu) Json.fromJson(menuN, Menu.class);
		}
		return menu;
	}
	
}
