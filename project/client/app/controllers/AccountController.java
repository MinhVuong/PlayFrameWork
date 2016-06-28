package controllers;

import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;

import PakageResult.ActiveAccountPakage;
import PakageResult.InforPakage;
import PakageResult.LoginPakage;
import PakageResult.RegisterPakage;
import PakageResult.User;
import business.MyCrypto;

import com.fasterxml.jackson.databind.JsonNode;

import models.Address;
import models.CategoryEntity;
import models.CategoryProductEntity;
import models.ChangePass;
import models.Infor;
import models.Login;
import models.Menu;
import models.Register;
import models.ProductEntity;
import play.Logger;
import play.Routes;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Session;
import views.html.*;
import play.api.libs.Crypto;
import play.cache.*;



public class AccountController extends Controller {

	private final Logger.ALogger log = Logger.of(AccountController.class);
	private final Crypto crypto;
	
	@Inject
	public AccountController(final Crypto cry)
	{
		this.crypto = cry;
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
	public Result login()
	{
		Menu menu = new Menu();
		menu = GetMenuFromSession();
		session().remove("ID");
		log.trace("Show Login");
		return ok(login.render("", "", menu.getCategories(), menu.getCategoryProducts()));
	}
	public CompletionStage<Result> loginSubmit()
	{
		log.info("Login Submit");
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		Session session = Http.Context.current().session();
		
		
		Login login_form = Form.form(Login.class).bindFromRequest().get();
		
		
		
		log.info(login_form.getEmail() + login_form.getPassword());
		String url = "http://localhost:9001/login";
		
		JsonNode json = Json.toJson(login_form);
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(60000).post(json);
		
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		LoginPakage pakage = new LoginPakage();
    		pakage = Json.fromJson(jsonNode, LoginPakage.class);
            
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		log.info("Login Success !");
            		User user = pakage.getUser();
            		session.put("ID", Integer.toString(user.getId()));
            		session.put("user", Json.toJson(user).toString());
            		List<ProductEntity> smartphone = new ArrayList<ProductEntity>();
            		List<ProductEntity> laptop = new ArrayList<ProductEntity>();

            		//return ok(index.render(user, smartphone, 0, laptop, 0, menu.getCategories(), menu.getCategoryProducts()));
            		return redirect(routes.HomeController.index());
            		}
            	case 0:{
            		return ok(login.render("Account didn't create!","", menu.getCategories(), menu.getCategoryProducts()));
            		}
            	case -1:{
            		return ok(login.render("Password was fail!","", menu.getCategories(), menu.getCategoryProducts()));
            	}
            	case -2:{
            		return ok(login.render("Account didn't active!","", menu.getCategories(), menu.getCategoryProducts()));
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
	public CompletionStage<Result> registerSubmit()
	{
		log.info("Register Submit");
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		Register register = Form.form(Register.class).bindFromRequest().get();
		
		String url = "http://localhost:9001/register";
		CompletionStage<Result> result=null;
		
		JsonNode json = Json.toJson(register);
		WSRequest send = WS.url(url);
		CompletionStage<WSResponse> receive  = send.setRequestTimeout(60000).post(json);
		result = receive.thenApply(resp -> {
		            
		    		JsonNode jsonNode = resp.asJson();
		            RegisterPakage pakage = new RegisterPakage();
		            pakage = Json.fromJson(jsonNode, RegisterPakage.class);
		            if(resp.getStatus()== 200)
		            {	
		            	switch(pakage.getType())
		            	{
		            	case 0:{
		            		return ok(login.render("", "Now, you don't register acccount.\n Please register other time!", menu.getCategories(), menu.getCategoryProducts()));
		            	}
		            	case 1:{
		            		return ok(login.render("", "Register successful. \n Please check email to active account!", menu.getCategories(), menu.getCategoryProducts()));
		            	}
		            	case 2:{
		            		return ok(login.render("", "Email register had exist!\n Please choose other email!", menu.getCategories(), menu.getCategoryProducts()));
		            	}
		            	case 3:{
		            		return ok(login.render("", "Register Fail.\n Server didn't send email to you!", menu.getCategories(), menu.getCategoryProducts()));
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

	public CompletionStage<Result> activaAccount(String token, String id){
		Session session = Http.Context.current().session();
		String url = "http://localhost:9001/activeAccount/"+token+"/"+id;
		CompletionStage<WSResponse> receive  = WS.url(url).get();
		CompletionStage<Result> result = receive.thenApply(resp -> {
			log.info("status active: " + resp.getStatus());
			JsonNode jsonNode = resp.asJson();
			ActiveAccountPakage pakage = new ActiveAccountPakage();
			pakage = Json.fromJson(jsonNode, ActiveAccountPakage.class);
			if(resp.getStatus()== 200)
            {	
            	session.put("user", Json.toJson(pakage.getUser()).toString());
            	return redirect(routes.HomeController.index());
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		return result;
	}
	
	public CompletionStage<Result> infor()
	{
		log.info("GET Infor");
		
		Session session = Http.Context.current().session();
		String id = session("ID");
		log.info(id);
		String url = "http://localhost:9001/infor/"+id;
		//log.info(url);
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(60000).get();
		CompletionStage<Result> result = receive.thenApply(resp -> {
			JsonNode jsonNode = resp.asJson();
			InforPakage pakage = new InforPakage();
			pakage = Json.fromJson(jsonNode, InforPakage.class);
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		Infor infor = pakage.getInfor();
            		Address address = pakage.getAddress();
            		return ok(infor1.render(infor, address, menu.getCategories(), menu.getCategoryProducts()));
            		}
            	case 0:{
            		return ok("0");
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
	public CompletionStage<Result> updateInfor()
	{
		log.info("Update Infor");
		Infor infor = Form.form(Infor.class).bindFromRequest().get();
		Session session = Http.Context.current().session();
		String id = session("ID");
		infor.setId(Integer.parseInt(id));
		
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		String url = "http://localhost:9001/updateInfor";
		JsonNode json = Json.toJson(infor);
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(60000).post(json);
		
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
			JsonNode jsonNode = resp.asJson();
			InforPakage pakage = new InforPakage();
			pakage = Json.fromJson(jsonNode, InforPakage.class);
            
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		Infor inforP = pakage.getInfor();
            		Address address = pakage.getAddress();
            		return ok(infor1.render(inforP, address, menu.getCategories(), menu.getCategoryProducts()));
            		}
            	case 0:{
            		return ok("0");
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
	
	public CompletionStage<Result> address()
	{
		log.info("Update Address!");
		Address address = new Address();
		address = Form.form(Address.class).bindFromRequest().get();
		Session session = Http.Context.current().session();
		String id = session("ID");
		address.setId(Integer.parseInt(id));
		
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		String url = "http://localhost:9001/updateAddress";
		JsonNode json = Json.toJson(address);
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(60000).post(json);
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
			JsonNode jsonNode = resp.asJson();
			InforPakage pakage = new InforPakage();
			pakage = Json.fromJson(jsonNode, InforPakage.class);
            
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		Infor inforP = pakage.getInfor();
            		Address addressP = pakage.getAddress();
            		return ok(infor1.render(inforP, addressP, menu.getCategories(), menu.getCategoryProducts()));
            		}
            	case 0:{
            		return ok("0");
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
	
	public CompletionStage<Result> changePass()
	{
		log.info("Change Password!");
		ChangePass changePass = new ChangePass();
		changePass = Form.form(ChangePass.class).bindFromRequest().get();
		Session session = Http.Context.current().session();
		String id = session("ID");
		changePass.setId(Integer.parseInt(id));
		
		Menu menu = new Menu();
		Menu menuS = GetMenuFromSession();
		menu.setCategories(menuS.getCategories());
		menu.setCategoryProducts(menuS.getCategoryProducts());
		
		String url = "http://localhost:9001/changePass";
		JsonNode json = Json.toJson(changePass);
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(60000).post(json);
		
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
			JsonNode jsonNode = resp.asJson();
			InforPakage pakage = new InforPakage();
			pakage = Json.fromJson(jsonNode, InforPakage.class);
            
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		Infor inforP = pakage.getInfor();
            		Address addressP = pakage.getAddress();
            		return ok(infor1.render(inforP, addressP, menu.getCategories(), menu.getCategoryProducts()));
            		}
            	case 0:{
            		return ok("0");
            		}
            	case -1:{
            		return ok("-1");
            		}
            	case -2:{
            		return ok("-2");
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
	
	public Result forgot()
	{
		
		return ok("ok");
	}
	
	
	public Result encrypt()
	{
		MyCrypto myCryp = new MyCrypto(this.crypto);
		@SuppressWarnings("deprecation")
		String result = myCryp.encrypt("minhvuongtran");
		result += " ";
		result += myCryp.decrypt(result);
		return ok(result);
	}
	
	public Result demoajax(){
		return ok("ok");
	}
	
	
}
