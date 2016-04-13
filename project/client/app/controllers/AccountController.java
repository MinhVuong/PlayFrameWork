package controllers;

import java.util.concurrent.CompletionStage;

import PakageResult.RegisterPakage;

import com.fasterxml.jackson.databind.JsonNode;

import models.Register;
import play.Logger;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class AccountController extends Controller {

	private final Logger.ALogger log = Logger.of("AccountController");
	public Result login()
	{
		log.info("Show Login");
		return ok(login.render("", ""));
	}
	public Result loginSubmit()
	{
		log.info("Login Submit");
		
		return ok(index.render(""));
	}
	public CompletionStage<Result> registerSubmit()
	{
		log.info("Register Submit");
		Register register = Form.form(Register.class).bindFromRequest().get();
		
		String url = "http://localhost:9001/register";
		CompletionStage<Result> result=null;
		
		JsonNode json = Json.toJson(register);
		WSRequest send = WS.url(url);
		CompletionStage<WSResponse> receive  = send.post(json);
		result = receive.thenApply(resp -> {
		            
		    		JsonNode jsonNode = resp.asJson();
		            RegisterPakage pakage = new RegisterPakage();
		            pakage = Json.fromJson(jsonNode, RegisterPakage.class);
		            if(resp.getStatus()== 200)
		            {	
		            	switch(pakage.getType())
		            	{
		            	case 0:{
		            		return ok(login.render("", "Now, you don't register acccount.\n Please register other time!"));
		            	}
		            	case 1:{
		            		return ok(login.render("", "Register successful. \n Please check email to active account!"));
		            	}
		            	case 2:{
		            		return ok(login.render("", "Email register had exist!\n Please choose other email!"));
		            	}
		            	case 3:{
		            		return ok(login.render("", "Register Fail.\n Server didn't send email to you!"));
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
		return ok(forgot.render(""));
	}
	public Result infor()
	{
		return ok(infor.render(""));
	}
	public Result address()
	{
		return ok(address.render(""));
	}
	
	
}
