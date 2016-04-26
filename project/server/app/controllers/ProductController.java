package controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import models.IndexRequest;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import entities.ProductEntity;
import pakageResult.IndexFullPakage;
import pakageResult.IndexPakage;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.ProductService;



public class ProductController extends Controller{

	private ProductService productS = new ProductService();
	
	
	
	public Result productList()
	{
		JsonNode json = request().body().asJson();
		IndexRequest indexR = new IndexRequest();
		CompletionStage<Result> result = null;
		
		indexR = Json.fromJson(json, IndexRequest.class);
		
		
		List<ProductEntity> smartphones =  productS.GetProductsByPage(indexR.getPageSmartphone(), indexR.getCountSmartphone(), 1);
		List<ProductEntity> laptops =  productS.GetProductsByPage(indexR.getPageLaptop(), indexR.getCountLaptop(), 2);
		IndexPakage indexP = new IndexPakage();
		IndexFullPakage pakage = new IndexFullPakage();
		
		if(smartphones.isEmpty() || laptops.isEmpty())
		{
			indexP.setType(0);
			pakage.setType(0);
		}
		else
		{
			indexP.setType(1);
			pakage.setType(1);
			if(!smartphones.isEmpty())
			{
				indexP.setSmartphones(smartphones);
				int pageS = productS.PageTotal(1, indexR.getCountSmartphone());
				indexP.setPageSmartphone(pageS);
			}
			if(!laptops.isEmpty())
			{
				indexP.setLaptop(laptops);
				int pageL = productS.PageTotal(2, indexR.getCountLaptop());
				indexP.setPagelaptop(pageL);
			}
			pakage.setIndexPakage(indexP);
			List<CategoryEntity> categories = productS.GetCategory();
			pakage.setCategories(categories);
			for(CategoryEntity category : categories){
				List<CategoryProductEntity> categoryProducts = productS.GetCategoryProductByIdCategory(category.getId());
				pakage.addCategoryProducts(categoryProducts);
			}
		}
		
		return ok(Json.toJson(pakage));
	}
	
	public Result category(int id)
	{
		List<CategoryProductEntity> categoryProducts = productS.GetCategoryProductByIdCategory(id);
		for(CategoryProductEntity categoryProduct : categoryProducts){
			List<ProductEntity> products = productS.GetProductByCategory(categoryProduct.getId());
			
		}
		
		
		
		return ok("ok");
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result productListAjax()
	{
		JsonNode json = request().body().asJson();
		IndexRequest indexR = new IndexRequest();
		if(json == null)
			return ok("null");
		
		String count = json.findPath("countSmartphone").textValue();
		String page = json.findPath("pageSmartphone").textValue();
		if(count.equals(null))
			return ok("null");
		indexR.setCountSmartphone(Integer.parseInt(count));
		indexR.setPageSmartphone(Integer.parseInt(page));
			
		List<ProductEntity> products =  productS.GetProductsByPage(indexR.getPageSmartphone(), indexR.getCountSmartphone());
		IndexPakage pakage = new IndexPakage();
		if(products.isEmpty())
		{
			pakage.setType(0);
			
		}
		else
		{
			pakage.setType(1);
			pakage.setSmartphones(products);
			
		}
		return ok(Json.toJson(pakage));
	}
	
	@AddCSRFToken
	public Result token()
	{
		Optional<CSRF.Token> token = CSRF.getToken(request());
		String result = token.get().value();
		response().setCookie("Csrf-Token", result);
		
		return ok(result);
	}
	
	@RequireCSRFCheck
	public Result demoCsrf()
	{
		
		return ok(CSRF.getToken(request()).map(t -> t.value()).orElse("no token"));
	}
	
}
