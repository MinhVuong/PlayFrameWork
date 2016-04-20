package controllers;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import PakageResult.InforPakage;
import PakageResult.LoginPakage;
import PakageResult.RegisterPakage;
import PakageResult.User;
import business.MyCrypto;

import com.fasterxml.jackson.databind.JsonNode;

import models.Address;
import models.ChangePass;
import models.Infor;
import models.Login;
import models.Register;
import play.Logger;
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

	private final Logger.ALogger log = Logger.of("AccountController");
	private final Crypto crypto;
	
	@Inject
	public AccountController(final Crypto cry)
	{
		this.crypto = cry;
	}
	public Result login()
	{
		session().clear();
		log.info("Show Login");
		return ok(login.render("", ""));
	}
	public CompletionStage<Result> loginSubmit()
	{
		log.info("Login Submit");
		
		Session session = Http.Context.current().session();
		Login login_form = Form.form(Login.class).bindFromRequest().get();
		String url = "http://localhost:9001/login";
		
		JsonNode json = Json.toJson(login_form);
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
		
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		LoginPakage pakage = new LoginPakage();
    		pakage = Json.fromJson(jsonNode, LoginPakage.class);
            
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		User user = pakage.getUser();
            		session.put("ID", Integer.toString(user.getId()));
            		return ok(index.render(user, null, null, null, null));
            		}
            	case 0:{
            		return ok(login.render("Account didn't create!",""));
            		}
            	case -1:{
            		return ok(login.render("Password was fail!",""));
            	}
            	case -2:{
            		return ok(login.render("Account didn't active!",""));
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

	
	public CompletionStage<Result> infor()
	{
		log.info("GET Infor");
		String id = session("ID");
		log.info(id);
		String url = "http://localhost:9001/infor/"+id;
		log.info(url);
		CompletionStage<WSResponse> receive  = WS.url(url).get();
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
            		return ok(infor1.render(infor, address));
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
		String id = session("ID");
		infor.setId(Integer.parseInt(id));
		
		String url = "http://localhost:9001/updateInfor";
		JsonNode json = Json.toJson(infor);
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
		
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
            		return ok(infor1.render(inforP, address));
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
		String id = session("ID");
		address.setId(Integer.parseInt(id));
		String url = "http://localhost:9001/updateAddress";
		JsonNode json = Json.toJson(address);
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
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
            		return ok(infor1.render(inforP, addressP));
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
		String id = session("ID");
		changePass.setId(Integer.parseInt(id));
		String url = "http://localhost:9001/changePass";
		JsonNode json = Json.toJson(changePass);
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
		
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
            		return ok(infor1.render(inforP, addressP));
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
	
	
}
