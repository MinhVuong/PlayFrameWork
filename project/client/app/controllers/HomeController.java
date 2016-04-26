package controllers;

import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.IndexRequest;
import models.ProductEntity;
import models.CategoryEntity;
import models.CategoryProductEntity;
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
            		
            		return ok(index.render(user, smartphone, pageS, laptop, pageL));
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
    
    public Result category()
    {
    	User user = new User();
    	return ok(category.render(user));
    }

   
}
