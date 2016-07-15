package controllers;



import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;




import models.Login;

import com.fasterxml.jackson.databind.JsonNode;

import pakageResultAdmin.LoginAdminPakage;
import play.api.libs.Crypto;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ACustomerService;
import services.AdminRoleService;
import services.CategoryProductService;
import services.CategoryService;
import services.CustomerService;
import views.html.*;

public class AAccountController extends Controller{
	private final Crypto crypto;
	private ACustomerService aCustomerS = new ACustomerService();
	private AdminRoleService adminRoleS = new AdminRoleService();
	private CustomerService customerS = new CustomerService();
	private CategoryService categoryS = new CategoryService();
	private CategoryProductService categoryProductS = new CategoryProductService();
	
	@Inject
	public AAccountController(final Crypto cryp)
	{
		this.crypto = cryp;
	}
	
	public CompletionStage<Result> login() {
		Result resp = new Result(200);
		Login login = new Login();
		JsonNode json = request().body().asJson();
		login = Json.fromJson(json, Login.class);
		int result = aCustomerS.CheckLoginAdmin(login, this.crypto);
		
		LoginAdminPakage pakage = new LoginAdminPakage();
		pakage.setType(result);
		if(result == 1){
			int group = aCustomerS.GetRoleAdmin(login, crypto);
			pakage.setUser(customerS.CreateUser(login));
			pakage.setRules(adminRoleS.GetListRuleByIdAdmin(group));
			pakage.setRole(group);
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(401, "");
		}
        return CompletableFuture.completedFuture(resp);
    }
	
	
}

