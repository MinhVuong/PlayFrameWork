package controllers;

import java.util.concurrent.CompletionStage;

import models.AddCount;
import models.CartProducts;
import models.Menu;
import models.ProductCart;
import models.ProductEntity;
import models.CartEdit;
import business.SessionManager;

import com.fasterxml.jackson.databind.JsonNode;

import PakageResult.ProductPakage;
import PakageResult.User;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.Session;
import views.html.*;

public class ProductController extends Controller{
	SessionManager sessionM = new SessionManager();
	private final Logger.ALogger log = Logger.of("product");
	public CompletionStage<Result> cart(int id)
	{
		Session session = Http.Context.current().session();
		CartProducts productsS = sessionM.GetCartProducts();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		
		String url = "http://localhost:9001/product/" + id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductPakage pakage = new ProductPakage();
    		pakage = Json.fromJson(jsonNode, ProductPakage.class);
    		
    		User user = new User(user1);
			Menu menu = new Menu(menu1);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            			CartProducts products = new CartProducts(productsS);
            			
            			ProductCart product = new ProductCart();
            			product = pakage.getProduct();
            			if(products==null)
            			{
            				products = new CartProducts();
            			}
            			
            			products.AddProduct(product);
            			session.put("products", Json.toJson(products).toString());
            			
            			
            			return ok(cart.render("", user, menu.getCategories(), menu.getCategoryProducts(), products));
            		}
            	case 0:{
            		return ok(cart.render("", user, menu.getCategories(), menu.getCategoryProducts(), productsS));
            		}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;
	}
	
	public Result cartAdd()
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.AddCountProduct(add.getId());
			
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			edit.setTotal(products.GetTotalProduct(add.getId()));
			edit.setTotalCart(products.GetPricesTotal());
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}
	public Result cartSub()
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.SubCountProduct(add.getId());
			
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			edit.setTotal(products.GetTotalProduct(add.getId()));
			edit.setTotalCart(products.GetPricesTotal());
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}
	public Result cartDelete()
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.DeleteCountProduct(add.getId());
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			edit.setTotal(products.GetTotalProduct(add.getId()));
			edit.setTotalCart(products.GetPricesTotal());
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}

	public Result checkout()
	{
		CartProducts productsS = sessionM.GetCartProducts();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		if(productsS.getProducts().size() == 0)
		{
			String message = "Cart empty!";
			return ok(cart.render(message, user1, menu1.getCategories(), menu1.getCategoryProducts(), productsS));
		}
		else
			return ok(checkout.render(user1, menu1.getCategories(), menu1.getCategoryProducts(), productsS));
	}
	
	public Result checkoutSuccess()
	{
		return ok("checkout success");
	}
	public Result checkoutFail()
	{
		return ok("checkout fail");
	}
}
