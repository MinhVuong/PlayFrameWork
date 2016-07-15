package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import pakageResult.SearchPakage;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ProductService;
import services.SearchService;

public class SearchController extends Controller{
	private Logger.ALogger logger = Logger.of("search");
	private SearchService searchS = new SearchService();
	private ProductService productS = new ProductService();
	
	public CompletionStage<Result> search(int cate, String key){
		Result result = new Result(200);
		try{
			SearchPakage pakage = new SearchPakage();
			pakage.setProducts(searchS.Search(cate, key));
			
			result = ok(Json.toJson(pakage));
		}catch(Exception ex){
			result = status(500, "");
		}
		return CompletableFuture.completedFuture(result);
	}
}
