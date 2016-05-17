package controllers;
import helper.SessionHelper;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProductEntityShow;
import models.ProductShow;
import models.User;
import entities.CategoryProductEntity;
import entities.ProductEntity;
import pakageResult.ProductAdminPakage;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ProductController extends Controller{
	private SessionHelper sessionH = new SessionHelper();
	private final Logger.ALogger log = Logger.of("product");
	
	public CompletionStage<Result> products(){
		User user = sessionH.GetUser("user");
		String url = "http://localhost:9001/admin/pro/all";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductAdminPakage pakage = new ProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys));
            	}
            	case 0:{
            		return ok("Server fail!");
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
	
	
	public CompletionStage<Result> productAdd(){
		User user = sessionH.GetUser("user");
		ProductEntityShow data =  Form.form(ProductEntityShow.class).bindFromRequest().get();
		data.setId(0);
		ProductEntity entity = new ProductEntity();
		entity.ConvertByProductShow(data);
		//String name = entity.getName().replace(' ', ';');
		//entity.setName(name);
		log.info("entity: " + Json.toJson(entity).toString());
		JsonNode json = Json.toJson(entity);
		String url = "http://localhost:9001/admin/pro/add";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductAdminPakage pakage = new ProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys));
            	}
            	case 0:{
            		return ok("Server fail!");
            	}
            	case -1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys));
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
	
	public CompletionStage<Result> productDel(int id){
		User user = sessionH.GetUser("user");
		
		String url = "http://localhost:9001/admin/pro/del/"+id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductAdminPakage pakage = new ProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys));
            	}
            	case 0:{
            		return ok("Server fail!");
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
	
	public CompletionStage<Result> productEdit(){
		User user = sessionH.GetUser("user");
		ProductEntityShow data =  Form.form(ProductEntityShow.class).bindFromRequest().get();
		ProductEntity entity = new ProductEntity();
		entity.ConvertByProductShow(data);
		//String name = entity.getName().replace(' ', ';');
		//entity.setName(name);
		log.info("entity: " + Json.toJson(entity).toString());
		JsonNode json = Json.toJson(entity);
		String url = "http://localhost:9001/admin/pro/edit";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductAdminPakage pakage = new ProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys));
            	}
            	case 0:{
            		return ok("Server fail!");
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
	
	public Result cacs(){
		return ok("hehe");
	}
	
	public Result productShowEdit(int id){
		return ok(" " +id);
	}
}
