package controllers;

import java.util.List;



import models.IndexRequest;

import com.fasterxml.jackson.databind.JsonNode;

import entities.ProductEntity;
import pakageResult.IndexPakage;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.ProductService;



public class ProductController extends Controller{

	private ProductService productS = new ProductService();
	
	
	
	public Result productList()
	{
		JsonNode json = request().body().asJson();
		IndexRequest indexR = new IndexRequest();
		
		
		indexR = Json.fromJson(json, IndexRequest.class);
		
		
		List<ProductEntity> smartphones =  productS.GetProductsByPage(indexR.getPageSmartphone(), indexR.getCountSmartphone(), 1);
		List<ProductEntity> laptops =  productS.GetProductsByPage(indexR.getPageLaptop(), indexR.getCountLaptop(), 2);
		IndexPakage pakage = new IndexPakage();
		if(smartphones.isEmpty() || laptops.isEmpty())
		{
			pakage.setType(0);
			return ok("null");
		}
		else
		{
			pakage.setType(1);
			if(!smartphones.isEmpty())
			{
				pakage.setSmartphones(smartphones);
				int pageS = productS.PageTotal(1, indexR.getCountSmartphone());
				pakage.setPageSmartphone(pageS);
			}
			if(!laptops.isEmpty())
			{
				pakage.setLaptop(laptops);
				int pageL = productS.PageTotal(2, indexR.getCountLaptop());
				pakage.setPagelaptop(pageL);
			}
			return ok(Json.toJson(pakage));
		}
	}
	
	public Result category()
	{
		return ok("vuong");
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
	
	
}
