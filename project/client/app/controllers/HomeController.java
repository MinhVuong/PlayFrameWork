package controllers;

import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.IndexRequest;
import models.ProductEntity;
import models.CategoryEntity;
import models.CategoryProductEntity;
import models.CategoryProduct;
import models.Menu;
import PakageResult.CategoryProductPakage;
import PakageResult.CategoryProduct2Pakage;
import PakageResult.IndexPakage;
import PakageResult.IndexFullPakage;
import PakageResult.User;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.*;
import play.mvc.Http.Session;
import views.html.*;

import java.util.List;




/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	private final Logger.ALogger log = Logger.of("home");
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> index() {
    	User user = new User();
    	Session session = Http.Context.current().session();
    	String userS = session("user");
    	
    	if(userS != null)
    	{
	    	log.info("UserS: " + userS);
	    	JsonNode userJ = Json.parse(userS);
	    	User user1 = Json.fromJson(userJ, User.class);
	    	user.setId(user1.getId());
	    	user.setFirstName(user1.getFirstName());
    	}
    	
    	
    	IndexRequest indexR = new IndexRequest();
    	indexR.setCountSmartphone(10);
    	indexR.setPageSmartphone(1);
    	indexR.setCountLaptop(10);
    	indexR.setPageLaptop(1);
    	
    	JsonNode json = Json.toJson(indexR);
    	String url = "http://localhost:9001/productList";
    	
    	CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		IndexFullPakage pakageF = new IndexFullPakage();
    		pakageF = Json.fromJson(jsonNode, IndexFullPakage.class);
    		
    		IndexPakage pakage = pakageF.getIndexPakage();
    		List<CategoryEntity> categories = pakageF.getCategories();
    		List<List<CategoryProductEntity>> categoryProducts = pakageF.getCategoryProducts();
    		Menu menu = new Menu(categories, categoryProducts);
    		session.put("menu", Json.toJson(menu).toString());
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		//List<ProductEntity> products = new ArrayList<ProductEntity>();
            		List<ProductEntity> smartphone = pakage.getSmartphones();
            		List<ProductEntity> laptop = pakage.getLaptop();
            		int pageS = pakage.getPageSmartphone();
            		int pageL = pakage.getPagelaptop();
            		
            		return ok(index.render(user, smartphone, pageS, laptop, pageL, categories, categoryProducts));
            		}
            	case 0:{
            		return ok(errorp.render("Didn't connect server !!!"));
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
    
    public CompletionStage<Result> category(int id)
    {
    	log.info("Method category: " + id);
    	User user1 = GetUserFromSession();
    	
		Menu menu1 = GetMenuFromSession();
		
    	String url = "http://localhost:9001/category/" + id;
    	
    	CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProductPakage pakage = new CategoryProductPakage();
    		pakage = Json.fromJson(jsonNode, CategoryProductPakage.class);

            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		User user = new User(user1);
            		Menu menu = new Menu(menu1);
            		
            		List<CategoryProduct> categories = pakage.getCategoryProducts();
            		List<List<ProductEntity>> categoryProducts = pakage.getProductss();
            		String name = pakage.getName();
            		
            		return ok(category.render(user, menu.getCategories(), menu.getCategoryProducts(), name, categories, categoryProducts));
            		}
            	case 0:{
            		return ok(errorp.render("Didn't connect server !!!"));
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
    
    public CompletionStage<Result> categoryProduct(int id, int idp)
    {
    	log.info("Method categoryProduct: " + id + " - idp: " + idp);
    	
    	User user1 = GetUserFromSession();
    	User user = new User(user1);
    	
		Menu menu1 = GetMenuFromSession();
		Menu menu = new Menu(menu1);
		
    	String url = "http://localhost:9001/categoryProduct/" + id + "/" + idp;
    	
    	CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProduct2Pakage pakage = new CategoryProduct2Pakage();
    		pakage = Json.fromJson(jsonNode, CategoryProduct2Pakage.class);

            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		
            		String name = pakage.getName();
            		List<ProductEntity> products = pakage.getProducts();
            		
            		return ok(categoryProduct.render(user, menu.getCategories(), menu.getCategoryProducts(), name, products));
            		}
            	case 0:{
            		return ok(errorp.render("Didn't connect server !!!"));
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
    private Menu GetMenuFromSession()
	{
		Menu menu = new Menu();
		String menuS = session("menu");
		if(menuS != null)
		{
			JsonNode menuN = Json.parse(menuS);
			menu = (Menu) Json.fromJson(menuN, Menu.class);
		}
		return menu;
	}
    
    private User GetUserFromSession()
	{
		User user = new User();
		String userS = session("user");
		if(userS != null)
		{
			JsonNode userN = Json.parse(userS);
			user = (User) Json.fromJson(userN, User.class);
		}
		return user;
	}
}
