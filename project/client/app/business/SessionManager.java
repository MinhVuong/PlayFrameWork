package business;
import models.Address;
import models.CartProducts;
import models.InforCompare;
import models.Menu;
import models.ProductEntity;
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
	
	public Address GetAddress(){
		Address address= new Address();
		Session sessionM = Http.Context.current().session();
		String addS = sessionM.get("addressc");
		if(addS != null){
			JsonNode json = Json.parse(addS);
			address = (Address) Json.fromJson(json, Address.class);
		}
		return address;
	}
	public int GetIdAddress(){
		int id=0;
		Session sessionM = Http.Context.current().session();
		String addS = sessionM.get("idA");
		if(addS != null){
			id = Integer.parseInt(addS);
		}
		return id;
	}
	
	public int GetIdCart(){
		int id=0;
		Session sessionM = Http.Context.current().session();
		String addS = sessionM.get("idc");
		if(addS != null){
			id = Integer.parseInt(addS);
		}
		return id;
	}
	
	public ProductEntity getProductCompare1(){
		Session sessionM = Http.Context.current().session();
		ProductEntity product = new ProductEntity();
		String comS = sessionM.get("productCompare1");
		if(comS != null){
			JsonNode comJ = Json.parse(comS);
			product = (ProductEntity)Json.fromJson(comJ, ProductEntity.class);
		}
		return product;
	}
	
	public InforCompare getInforCompare1(){
		Session sessionM = Http.Context.current().session();
		InforCompare product = new InforCompare();
		String comS = sessionM.get("inforCompare1");
		if(comS != null){
			JsonNode comJ = Json.parse(comS);
			product = (InforCompare)Json.fromJson(comJ, InforCompare.class);
		}
		return product;
	}
	
	public InforCompare getInforCompare2(){
		Session sessionM = Http.Context.current().session();
		InforCompare product = new InforCompare();
		String comS = sessionM.get("inforCompare2");
		if(comS != null){
			JsonNode comJ = Json.parse(comS);
			product = (InforCompare)Json.fromJson(comJ, InforCompare.class);
		}
		return product;
	}
	public ProductEntity getProductCompare2(){
		Session sessionM = Http.Context.current().session();
		ProductEntity product = new ProductEntity();
		String comS = sessionM.get("productCompare2");
		if(comS != null){
			JsonNode comJ = Json.parse(comS);
			product = (ProductEntity)Json.fromJson(comJ, ProductEntity.class);
		}
		return product;
	}
	
}
