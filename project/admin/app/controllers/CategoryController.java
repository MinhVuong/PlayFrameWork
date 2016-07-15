package controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import models.Category;
import models.CategoryEdit;
import models.CategoryProduct;
import models.CategoryProductAdd;
import models.CategoryProductShow;
import models.User;
import helper.SessionHelper;
import pakageResult.CategoryAddAdminPakage;
import pakageResult.CategoryAdminPakage;
import pakageResult.CategoryProductAdminPakage;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class CategoryController extends Controller{
	private final Logger.ALogger log = Logger.of("category");
	private SessionHelper sessionH = new SessionHelper();
	
	public CompletionStage<Result> category() {
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/admin/categories";
		
		
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
		
		
		
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryAdminPakage pakage = new CategoryAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<CategoryEntity> categorys = pakage.getCategory();
            		return ok(categories.render("", user, categorys, role));
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
	
	public CompletionStage<Result> categoryAdd() {
		
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		Category cate = Form.form(Category.class).bindFromRequest().get();
		String name = cate.getName().replace(' ', ';');
		
		String url = "http://localhost:9001/admin/category/add/" + name;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryAddAdminPakage.class);
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<CategoryEntity> cates = pakage.getCategories();
            		return ok(categories.render("", user, cates, role));
            	}
            	case 0:{
            		return ok("Server fail!");
            	}
            	case -1:{
            		List<CategoryEntity> cates = pakage.getCategories();
            		return ok(categories.render("", user, cates, role));
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
	public CompletionStage<Result> categoryEdit() {
		int role = sessionH.GetRole();
		User user = sessionH.GetUser("user");
		CategoryEdit cate = Form.form(CategoryEdit.class).bindFromRequest().get();
		String name = cate.getName().replace(' ', ';');
		cate.setName(name);
		
		JsonNode json = Json.toJson(cate);
		
		String url = "http://localhost:9001/admin/category/edit";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryAddAdminPakage.class);
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<CategoryEntity> cates = pakage.getCategories();
            		return ok(categories.render("", user, cates, role));
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
	
	public CompletionStage<Result> categoryDel(int id) {
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/admin/category/delete/"+id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryAddAdminPakage.class);
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<CategoryEntity> cates = pakage.getCategories();
            		return ok(categories.render("", user, cates, role));
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
	
	public Result categoryEdit(int id, String value) {
		User user = new User();
        return ok(id + " " + value);
    }
	
	
	public CompletionStage<Result> categoryProduct() {
		int role = sessionH.GetRole();
		User user = sessionH.GetUser("user");
		String url = "http://localhost:9001/admin/categoryProduct";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	
            	case 1:{
            		List<CategoryProductEntity> categories = pakage.getCategoryP();
            		List<CategoryEntity> cates = pakage.getCategories();
            		CategoryProductShow show = new CategoryProductShow(); 
            		show.CreateCategoryProductShow(categories, cates);;
            		return ok(categoryProducts.render(user, show.getList(), cates, role));
            	}
            	case 0:{
            		return ok("false");
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
	
	public CompletionStage<Result> catePAdd() {
		int role = sessionH.GetRole();
		User user = sessionH.GetUser("user");
		CategoryProductAdd add = Form.form(CategoryProductAdd.class).bindFromRequest().get();
		String name = add.getName().replace(' ', ';');
		CategoryProductEntity entity = new CategoryProductEntity();
		entity.setId(0);
		entity.setName(name);
		entity.setIdCategory(Integer.parseInt(add.getCategory()));
		log.info(add.getCategory());
		JsonNode json = Json.toJson(entity);
		
		String url = "http://localhost:9001/admin/cateP/add";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	
            	case 1:{
            		List<CategoryProductEntity> categories = pakage.getCategoryP();
            		List<CategoryEntity> cates = pakage.getCategories();
            		CategoryProductShow show = new CategoryProductShow(); 
            		show.CreateCategoryProductShow(categories, cates);;
            		return ok(categoryProducts.render(user, show.getList(), cates, role));
            	}
            	case -1:{
            		List<CategoryProductEntity> categories = pakage.getCategoryP();
            		List<CategoryEntity> cates = pakage.getCategories();
            		CategoryProductShow show = new CategoryProductShow(); 
            		show.CreateCategoryProductShow(categories, cates);;
            		return ok(categoryProducts.render(user, show.getList(), cates, role));
            	}
            	case 0:{
            		return ok("false");
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
	public CompletionStage<Result> catePEdit(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		CategoryProductEntity entity  = Form.form(CategoryProductEntity.class).bindFromRequest().get();
		String name = entity.getName().replace(' ', ';');
		entity.setName(name);
		
		JsonNode json = Json.toJson(entity);
		
		String url = "http://localhost:9001/admin/cateP/edit";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	
            	case 1:{
            		List<CategoryProductEntity> categories = pakage.getCategoryP();
            		List<CategoryEntity> cates = pakage.getCategories();
            		CategoryProductShow show = new CategoryProductShow(); 
            		show.CreateCategoryProductShow(categories, cates);;
            		return ok(categoryProducts.render(user, show.getList(), cates, role));
            	}
            	case 0:{
            		return ok("false");
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
	
	public CompletionStage<Result> catePDel(int id){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/admin/cateP/delete/" +id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
    		pakage = Json.fromJson(jsonNode, CategoryProductAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	
            	case 1:{
            		List<CategoryProductEntity> categories = pakage.getCategoryP();
            		List<CategoryEntity> cates = pakage.getCategories();
            		CategoryProductShow show = new CategoryProductShow(); 
            		show.CreateCategoryProductShow(categories, cates);;
            		return ok(categoryProducts.render(user, show.getList(), cates, role));
            	}
            	case 0:{
            		return ok("false");
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
	
	
}
