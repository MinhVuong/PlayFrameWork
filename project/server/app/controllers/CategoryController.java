package controllers;

import play.Logger;
import play.mvc.Controller;
import services.CategoryProductService;
import services.CategoryService;

import java.util.List;






import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import models.Categories;



import models.CategoryAdmin;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import pakageResult.CategoryProductPakage;
import pakageResultAdmin.CategoryAddAdminPakage;
import pakageResultAdmin.CategoryAdminPakage;
import pakageResultAdmin.CategoryProductAdminPakage;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class CategoryController extends Controller{
	private CategoryService categoryS = new CategoryService();
	private CategoryProductService categoryProductS = new CategoryProductService();
	
	private Logger.ALogger loger = Logger.of(CategoryController.class);
	
	
	
	public CompletionStage<Result> categories(){
		Result resp = new Result(200);
		CategoryAdminPakage pakage = new CategoryAdminPakage();
		List<CategoryEntity> categories = categoryS.GetList();
		
		if(categories != null){
			pakage.setCategory(categories);
			pakage.setType(1);
			resp = status(200, Json.toJson(pakage));
		}else{
			pakage.setType(0);
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	
	public CompletionStage<Result> categoryAdd(String name){
		Result resp = new Result(200);
		name = name.replace(';', ' ');
		CategoryEntity entity = new CategoryEntity();
		entity.setName(name);
		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
		int result = categoryS.AddCategory(entity);
		pakage.setType(result);
		if(result != 0){
			pakage.setCategories(categoryS.GetList());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> categoryDelete(int id){
		Result resp = new Result(200);
		boolean result = categoryS.DeleteCategory(id);
		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
		if(result){
			pakage.setType(1);
			pakage.setCategories(categoryS.GetList());
			resp = status(200, Json.toJson(pakage));
		}else{
			pakage.setType(0);
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> categoriesEdit(){
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		CategoryAdmin cate = Json.fromJson(json, CategoryAdmin.class);
		String name = cate.getName().replace(';', ' ');
		
		CategoryEntity entity = new CategoryEntity();
		entity.setId(cate.getId());
		entity.setName(name);
		
		
		CategoryAddAdminPakage pakage = new CategoryAddAdminPakage();
		int result = categoryS.EditCategory(entity);
		pakage.setType(result);
		if(result != 0){
			pakage.setCategories(categoryS.GetList());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> categoryProduct(){
		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
		pakage.setType(1);
		pakage.setCategoryP(categoryProductS.GetCategoryProductList());
		pakage.setCategories(categoryS.GetList());
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> catePAdd(){
		JsonNode json = request().body().asJson();
		CategoryProductEntity data = Json.fromJson(json, CategoryProductEntity.class);
		String name = data.getName().replace(';', ' ');
		CategoryProductEntity entity = new CategoryProductEntity();
		entity.setName(name);
		entity.setIdCategory(data.getIdCategory());
		
		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
		int result = categoryProductS.Add(entity);
		pakage.setType(result);
		pakage.setCategories(categoryS.GetList());
		pakage.setCategoryP(categoryProductS.GetCategoryProductList());
		
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> catePEdit(){
		JsonNode json = request().body().asJson();
		CategoryProductEntity data = Json.fromJson(json, CategoryProductEntity.class);
		String name = data.getName().replace(';', ' ');
		CategoryProductEntity entity = new CategoryProductEntity();
		entity.setId(data.getId());
		entity.setName(name);
		entity.setIdCategory(data.getIdCategory());
		
		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
		int result = categoryProductS.Update(entity);
		pakage.setType(result);
		pakage.setCategories(categoryS.GetList());
		pakage.setCategoryP(categoryProductS.GetCategoryProductList());
		
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> catePDel(int id){
		
		CategoryProductAdminPakage pakage = new CategoryProductAdminPakage();
		int result = categoryProductS.Delete(id);
		pakage.setType(result);
		pakage.setCategories(categoryS.GetList());
		pakage.setCategoryP(categoryProductS.GetCategoryProductList());
		
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	
	
	
	public Result demo(){
		CategoryEntity entity = new CategoryEntity();
		entity.setName("Tablet");
		boolean result = categoryS.CheckExists(entity);
		if(result)
			return ok("ok");
		else
			return ok("faile");
	}
	
}
