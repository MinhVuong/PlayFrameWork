package controllers;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.CustomerEntity;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import services.CustomerService;
import models.Register;
import business.AccountHelper;
import business.MailHelper;


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
		Register register = new Register();
		register.setFirstName("Vuong");
		register.setLastName("Tran");
		register.setGender(1);
		register.setEmail("minhvuongtran527@gmail.com");
		register.setPassword("minhvuong");
	
		CustomerEntity customer = accountHelper.generateCustomerEntityFromRegister(register);
		int result = accounts.AddCustomer(customer, this.mailerClient);
		switch(result)
		{
		case 0:{
			loger.info("Register Result: Didn't Register Account!");
			return ok(Integer.toString(result));
			}
		case 1:{
			loger.info("Register Result: Register Successful");
			return ok(Integer.toString(result));
			}
		case 2:{
			loger.info("Register Result: Email register had registed.");
			return ok(Integer.toString(result));
			}
		case 3:{
			loger.info("Register Result: Didn't send email active account, so register fail!");
			return ok(Integer.toString(result));
			}
		}
		return ok("");
	}
	
	
}
