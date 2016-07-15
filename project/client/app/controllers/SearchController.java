package controllers;

import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.FormSearch;
import models.Menu;
import PakageResult.SearchPakage;
import PakageResult.User;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class SearchController extends Controller {
	private final Logger.ALogger logger = Logger.of("search");

	public CompletionStage<Result> Search(int cate, String key){
    	User user1 = GetUserFromSession();
    	User user = new User(user1);
    	
		Menu menu1 = GetMenuFromSession();
		Menu menu = new Menu(menu1);
    	String url = "http://localhost:9001/search/" + cate + "/" + key;
    	
    	CompletionStage<WSResponse> receive  = WS.url(url).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            if(resp.getStatus()== 200)
            {	
            	JsonNode jsonNode = resp.asJson();
        		SearchPakage pakage = new SearchPakage();
        		pakage = Json.fromJson(jsonNode, SearchPakage.class);
        		
        		return ok(search.render(user, menu.getCategories(), menu.getCategoryProducts(), "Search", pakage.getProducts()));        	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		return result;
	}

	private Menu GetMenuFromSession() {
		Menu menu = new Menu();
		String menuS = session("menu");
		if (menuS != null) {
			JsonNode menuN = Json.parse(menuS);
			menu = (Menu) Json.fromJson(menuN, Menu.class);
		}
		return menu;
	}

	private User GetUserFromSession() {
		User user = new User();
		String userS = session("user");
		if (userS != null) {
			JsonNode userN = Json.parse(userS);
			user = (User) Json.fromJson(userN, User.class);
		}
		return user;
	}
	
	public CompletionStage<Result> SearchForm(){
    	User user1 = GetUserFromSession();
    	User user = new User(user1);
		Menu menu1 = GetMenuFromSession();
		Menu menu = new Menu(menu1);
		FormSearch searchf = Form.form(FormSearch.class).bindFromRequest().get();
		String key = searchf.getKey();
		key = key.toLowerCase();
		key = key.replace(' ', '_');
    	String url = "http://localhost:9001/search/3/" + key;
    	
    	CompletionStage<WSResponse> receive  = WS.url(url).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            if(resp.getStatus()== 200)
            {	
            	JsonNode jsonNode = resp.asJson();
        		SearchPakage pakage = new SearchPakage();
        		pakage = Json.fromJson(jsonNode, SearchPakage.class);
        		
        		return ok(search.render(user, menu.getCategories(), menu.getCategoryProducts(), "Search", pakage.getProducts()));        	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		return result;
	}

}
