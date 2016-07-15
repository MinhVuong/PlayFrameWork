package controllers;

import models.Login;
import models.User;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import views.html.*;
import play.mvc.*;
import helper.SessionHelper;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import entities.AdminRoleEntity;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Http.Session;
import pakageResult.CategoryProductAdminPakage;
import pakageResult.LoginAdminPakage;

public class AccountController extends Controller{
	private final Logger.ALogger log = Logger.of("account");
	private SessionHelper sessionH = new SessionHelper();
	
	public Result login() {
		User user = new User();
        return ok(login.render("", user));
    }
	public Result index() {
		User user = new User();
		user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
        return ok(index.render(user, role));
    }
	public CompletionStage<Result> submitLogin() {
		Session sessionM = Http.Context.current().session();
		Login login_form = Form.form(Login.class).bindFromRequest().get();
		log.info(login_form.getEmail());
		JsonNode json = Json.toJson(login_form);
		String url = "http://localhost:9001/admin/login";
		
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
		
		
    	CompletionStage<Result> result = receive.thenApply(resp -> {    		
            if(resp.getStatus()== 200)
            {	
            	JsonNode jsonNode = resp.asJson();
        		LoginAdminPakage pakage = new LoginAdminPakage();
        		pakage = Json.fromJson(jsonNode, LoginAdminPakage.class);
        		User user = new User();
        		
        		List<AdminRoleEntity> rules = pakage.getRules();
        		user = pakage.getUser();
        		sessionM.put("user", Json.toJson(user).toString());
        		sessionM.put("role", Integer.toString(pakage.getRole()));
        		log.info("Role: "+pakage.getRole());
        		return ok(index.render(user, pakage.getRole()));
            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return ok(login.render("", null));
             }
         });
		return result;
    }
	public Result logout() {
		Session sessionM = Http.Context.current().session();
		sessionM.clear();
		
		User user = new User();
        return ok(login.render("", user));
    }
	
	
	

}
