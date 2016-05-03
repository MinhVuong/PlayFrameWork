package controllers;

import java.util.concurrent.CompletionStage;

import models.AddCount;
import models.CartProducts;
import models.ProductCart;
import models.ProductEntity;
import business.SessionManager;

import com.fasterxml.jackson.databind.JsonNode;

import PakageResult.ProductPakage;
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
		
		
		String url = "http://localhost:9001/product/" + id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductPakage pakage = new ProductPakage();
    		pakage = Json.fromJson(jsonNode, ProductPakage.class);
    		
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
            			
            			return ok(cart.render("", products));
            		}
            	case 0:{
            		return ok(cart.render("", productsS));
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
			
			return ok(Json.toJson(add));
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
			
			return ok(Json.toJson(add));
		}
		else
			return badRequest();
	}
}
