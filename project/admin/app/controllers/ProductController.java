package controllers;
import helper.SessionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.ProductCountCate;
import models.ProductCountShow;
import models.ProductEntityShow;
import models.ProductShow;
import models.User;
import entities.CategoryProductEntity;
import entities.ProductCountEntity;
import entities.ProductEntity;
import pakageResult.ProductAdminPakage;
import pakageResult.ProductCountAdminPakage;
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
		int role = sessionH.GetRole();
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
            		return ok(listProduct.render("", user, show.getProducts(), categorys, role));
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
		int role = sessionH.GetRole();
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
            		return ok(listProduct.render("", user, show.getProducts(), categorys, role));
            	}
            	case 0:{
            		return ok("Server fail!");
            	}
            	case -1:{
            		List<ProductEntity> products = pakage.getProducts();
            		List<CategoryProductEntity> categorys = pakage.getCategories();
            		ProductShow show = new ProductShow();
            		show.CreateProductShow(products, categorys);
            		return ok(listProduct.render("", user, show.getProducts(), categorys, role));
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
		int role = sessionH.GetRole();
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
            		return ok(listProduct.render("", user, show.getProducts(), categorys, role));
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
		int role = sessionH.GetRole();
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
            		return ok(listProduct.render("", user, show.getProducts(), categorys, role));
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
	
	public CompletionStage<Result> cacs(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/admin/pro/cac/list";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductCountAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductCountEntity> cacs = pakage.getColors();
            		List<ProductCountShow> shows = new ArrayList<ProductCountShow>();
            		List<ProductCountCate> categorys = pakage.getCates();
            		for(ProductCountEntity p : cacs){
            			ProductCountShow show = new ProductCountShow();
            			for(ProductCountCate cate : categorys){
                			if(cate.getId() == p.getProductId())
                			{
                				show.ConvertFromProductCountEntity(p,cate.getId(), cate.getName());
                			}
                		}
            			shows.add(show);
            		}
            		
            		
            		return ok(colors.render("", user, shows, categorys, role));
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
	
	public CompletionStage<Result> cacAdd(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		ProductCountShow s = Form.form(ProductCountShow.class).bindFromRequest().get();
		log.info("price " + s.getPrice_S());
		ProductCountEntity entity = new ProductCountEntity();
		entity.ConvertFromProductCountShow(s);
		JsonNode json = Json.toJson(entity);
		
		log.info(" " +entity.getPrice());
		String url = "http://localhost:9001/admin/pro/cac/add";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductCountAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductCountEntity> cacs = pakage.getColors();
            		List<ProductCountShow> shows = new ArrayList<ProductCountShow>();
            		List<ProductCountCate> categorys = pakage.getCates();
            		for(ProductCountEntity p : cacs){
            			ProductCountShow show = new ProductCountShow();
            			for(ProductCountCate cate : categorys){
                			if(cate.getId() == p.getProductId())
                			{
                				show.ConvertFromProductCountEntity(p,cate.getId(), cate.getName());
                			}
                		}
            			shows.add(show);
            		}
            		return ok(colors.render("", user, shows, categorys, role));
            	}
            	case 0:{
            		return ok("Server fail!");
            	}
            	case -1:{
            		List<ProductCountEntity> cacs = pakage.getColors();
            		List<ProductCountShow> shows = new ArrayList<ProductCountShow>();
            		List<ProductCountCate> categorys = pakage.getCates();
            		for(ProductCountEntity p : cacs){
            			ProductCountShow show = new ProductCountShow();
            			for(ProductCountCate cate : categorys){
                			if(cate.getId() == p.getProductId())
                			{
                				show.ConvertFromProductCountEntity(p,cate.getId(), cate.getName());
                			}
                		}
            			shows.add(show);
            		}
            		return ok(colors.render("", user, shows, categorys, role));
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
	
	public CompletionStage<Result> cacDel(int id){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/admin/pro/cac/del/" + id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductCountAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductCountEntity> cacs = pakage.getColors();
            		List<ProductCountShow> shows = new ArrayList<ProductCountShow>();
            		List<ProductCountCate> categorys = pakage.getCates();
            		for(ProductCountEntity p : cacs){
            			ProductCountShow show = new ProductCountShow();
            			for(ProductCountCate cate : categorys){
                			if(cate.getId() == p.getProductId())
                			{
                				show.ConvertFromProductCountEntity(p,cate.getId(), cate.getName());
                			}
                		}
            			shows.add(show);
            		}
            		return ok(colors.render("", user, shows, categorys, role));
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
	
	public CompletionStage<Result> cacEdit(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		ProductCountShow s = Form.form(ProductCountShow.class).bindFromRequest().get();
		log.info("price " + s.getPrice_S());
		ProductCountEntity entity = new ProductCountEntity();
		entity.ConvertFromProductCountShow(s);
		entity.setId(s.getId());
		entity.setProductId(s.getId_cate());
		JsonNode json = Json.toJson(entity);
		
		log.info(" " +entity.getPrice());
		String url = "http://localhost:9001/admin/pro/cac/edit";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
    		pakage = Json.fromJson(jsonNode, ProductCountAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<ProductCountEntity> cacs = pakage.getColors();
            		List<ProductCountShow> shows = new ArrayList<ProductCountShow>();
            		List<ProductCountCate> categorys = pakage.getCates();
            		for(ProductCountEntity p : cacs){
            			ProductCountShow show = new ProductCountShow();
            			for(ProductCountCate cate : categorys){
                			if(cate.getId() == p.getProductId())
                			{
                				show.ConvertFromProductCountEntity(p,cate.getId(), cate.getName());
                			}
                		}
            			shows.add(show);
            		}
            		return ok(colors.render("", user, shows, categorys, role));
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
	
	
	public Result productShowEdit(int id){
		return ok(" " +id);
	}
}
