package controllers;




import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CustomerEntity;
import pakageResult.RegisterPakage;
import play.Logger;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import services.CustomerService;
import models.Register;
import business.AccountHelper;
import business.DateHelper;


public class AccountController extends Controller {

	private CustomerService accounts = new CustomerService();
	private AccountHelper accountHelper = new AccountHelper();
	private Logger.ALogger loger = Logger.of("accountController");
	private final MailerClient mailerClient;
	
	@Inject
	public AccountController(final MailerClient client)
	{
		this.mailerClient = client;
	}

	// submit Register
	public Result register()
	{
		loger.info("Submit Register");
		JsonNode json = request().body().asJson();
		Register register = Json.fromJson(json, Register.class);
	
		CustomerEntity customer = accountHelper.generateCustomerEntityFromRegister(register);
		int result = accounts.AddCustomer(customer, this.mailerClient);
		RegisterPakage pakage = new RegisterPakage();
		switch(result)
		{
		case 0:{
			loger.info("Register Result: Didn't Register Account!");
			pakage.setType(0);
			break;
			}
		case 1:{
			loger.info("Register Result: Register Successful");
			pakage.setType(1);
			break;
			}
		case 2:{
			loger.info("Register Result: Email register had registed.");
			pakage.setType(2);
			break;
			}
		case 3:{
			loger.info("Register Result: Didn't send email active account, so register fail!");
			pakage.setType(3);
			break;
			}	
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result activeAccount(String token, Integer id)
	{
		if(accounts.ActiveAccount(token, id) == 1)
			return ok("true");
		else
			return ok("false");
	}
	
	public Result expiry()
	{
		DateHelper date = new DateHelper();
		String start = date.getDateTimeCurrent();
		String stop = date.GetDateTimeExpiryEmail(start);
		return ok(start + " " + stop);
	}

	
	
	
}
