package controllers;




import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import entities.AddressEntity;
import entities.CustomerEntity;
import pakageResult.ActiveAccountPakage;
import pakageResult.InforPakage;
import pakageResult.LoginPakage;
import pakageResult.RegisterPakage;
import pakageResult.User;
import play.Logger;
import play.api.libs.Crypto;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import services.AddressService;
import services.CustomerService;
import models.Address;
import models.ChangePass;
import models.Infor;
import models.Login;
import models.Register;
import business.AccountHelper;
import business.DateHelper;


public class AccountController extends Controller {

	private CustomerService accounts = new CustomerService();
	private AddressService addressS = new AddressService();
	private AccountHelper accountHelper = new AccountHelper();
	private Logger.ALogger logger = Logger.of("accountController");
	private final MailerClient mailerClient;
	private final Crypto crypto;
	
	@Inject
	public AccountController(final MailerClient client, final Crypto cryp)
	{
		this.mailerClient = client;
		this.crypto = cryp;
	}
	

	// submit Register
	public Result register()
	{
		logger.info("Submit Register");
		JsonNode json = request().body().asJson();
		Register register = Json.fromJson(json, Register.class);
		//Tạo CustomerEntity trong đó password sẽ được mã hóa.
		CustomerEntity customer = accountHelper.generateCustomerEntityFromRegister(register, this.crypto);
		// Thêm customer vào database.
		int result = accounts.AddCustomer(customer, this.mailerClient);
		// Gói tin pakage trả về cho client.
		RegisterPakage pakage = new RegisterPakage();
		pakage.setType(result);
		switch(result)
		{
		case 0:{
			logger.info("Register Result: Didn't Register Account!");
			
			break;
			}
		case 1:{
			logger.info("Register Result: Register Successful");
			
			break;
			}
		case 2:{
			logger.info("Register Result: Email register had registed.");
			
			break;
			}
		case 3:{
			logger.info("Register Result: Didn't send email active account, so register fail!");
			break;
			}	
		}
		return ok(Json.toJson(pakage));
	}
	// Client active account
	public CompletionStage<Result> activeAccount(String token, Integer id)
	{
		logger.info("Customer active account with token: " + token + ", id: " +id);
		Result resp = new Result(200);
		if(accounts.ActiveAccount(token, id) == 1){
			ActiveAccountPakage pakage = new ActiveAccountPakage();
			CustomerEntity entity = accounts.GetCustomerFromID(id);
			User user = new User(entity.getId(), entity.getFirstName(), entity.getEmail(), "");
			pakage.setUser(user);
			resp = ok(Json.toJson(pakage));
		}
		else
			resp = status(500, "Strange response type");
		
		return CompletableFuture.completedFuture(resp);
	}
	// Login
	// Restul 1 if login success.
	// Result 0 if email didn't register.
	// Result -1 if password dot match.
	// Result -2 if account didn't active.
	public CompletionStage<Result> login() {
		
		Login login = new Login();
		JsonNode json = request().body().asJson();
		login = Json.fromJson(json, Login.class);
		logger.info("Client call Login with Email: "+login.getEmail());
		// Kiểm tra dữ liệu client login.
		int result = accounts.Login(login, this.crypto);
		// Gói tin LoginPakage gửi vể cho Client.
		LoginPakage pakage = new LoginPakage();
		pakage.setType(result);

		Result resp = ok("");
		if (result == 1) {
			User user = accounts.CreateUser(login);
			pakage.setUser(user);
			resp = status(200, Json.toJson(pakage));
		} else if (result == 0) {
			pakage.setUser(new User());
			resp = status(402, Json.toJson(pakage));
		} else if (result == -1) {
			pakage.setUser(new User());
			resp = status(403, Json.toJson(pakage));
		} else if (result == -2) {
			pakage.setUser(new User());
			resp = status(405, Json.toJson(pakage));
		}
		logger.info("status code login: " + resp.status());
		return CompletableFuture.completedFuture(resp);
	}

	// return 1 if change Password success.
	// return -1 if Password old not match.
	// return -2 if didn't exist customer from id
	// return 0 if didn't connect database.
	public CompletionStage<Result> changePass(){
		logger.info("Client call Change Password");
		Result resp = new Result(200);
		ChangePass changePass = new ChangePass();
		JsonNode json = request().body().asJson();
		changePass = Json.fromJson(json, ChangePass.class);
		// Thay đổi password dưới CSDL
		int result = accounts.ChangePassword(changePass, this.crypto);
		
		// Gói tin InforPakage gửi về cho client.
		InforPakage pakage = new InforPakage();
		pakage.setType(result);
		if(result != 0)
		{
			Infor infor = accounts.GetInfor(changePass.getId(), this.crypto);
			AddressEntity entity = addressS.GetAddress(changePass.getId());
			Address address = new Address();
			if(entity != null)
				address = addressS.ConvertAddressEntityToAddress(entity);
			
			pakage.setInfor(infor);
			pakage.setAddress(address);
			resp = ok(Json.toJson(pakage));
		}else{
			resp = status(500, Json.toJson(pakage));
		}
		
		return CompletableFuture.completedFuture(resp);
	}
	
	// return 1 nếu Client update infor thành công.
	// return 0 nếu Client không kết nối được với Server.
	// return -1 nếu không tồn tại customer
	public CompletionStage<Result> updateInfor()
	{
		logger.info("Client update Infor!");
		Result resp = new Result(200);
		Infor infor = new Infor();
		JsonNode json = request().body().asJson();
		infor = Json.fromJson(json, Infor.class);
		
		int result = accounts.UpdateInfor(infor);
		InforPakage pakage = new InforPakage();
		pakage.setType(result);
		if(result == 1)
		{
			Infor inforP = accounts.GetInfor(infor.getId(), this.crypto);
			pakage.setInfor(inforP);
			// Address
			AddressEntity entity = addressS.GetAddress(infor.getId());
			Address address = new Address();
			if(entity!=null)
				address = addressS.ConvertAddressEntityToAddress(entity);
			pakage.setAddress(address);
			resp = ok(Json.toJson(pakage));
		}
		else{
			resp = status(500, Json.toJson(pakage));
		}
		
		
		return CompletableFuture.completedFuture(resp);
	}

	// Return 1 nếu thành công.
	// Return 0 nếu thất bại.
	public CompletionStage<Result> infor (String id)
	{
		// Infor lấy và gửi về cho client.
		// Password sẽ được giải mã lại cho client.
		logger.info("Client view Infor!");
		Result resp = new Result(200);
		Infor infor = accounts.GetInfor(Integer.parseInt(id), this.crypto);
		InforPakage pakage = new InforPakage();
		if(infor != null)
		{
			pakage.setType(1);
			pakage.setInfor(infor);
			
			AddressEntity entity = addressS.GetAddress(infor.getId());
			Address address = new Address();
			if(entity != null)
				address = addressS.ConvertAddressEntityToAddress(entity);
			
			pakage.setAddress(address);
			resp = ok(Json.toJson(pakage));
		}
		else
		{
			resp = status(500, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	// Return 1 nếu thành công.
	// Return 0 nếu thất bại
	public CompletionStage<Result> updateAddress()
	{
		logger.info("Client update Address!");
		Result resp = new Result(200);
		Address address = new Address();
		JsonNode json = request().body().asJson();
		address = Json.fromJson(json, Address.class);
		
		AddressEntity addressE = addressS.ConvertAddressToAddressEntity(address); 
		boolean result = addressS.UpdateAddress(address.getId(), addressE);
		InforPakage pakage = new InforPakage();
			
		if(result)
		{
			pakage.setType(1);
			Infor inforP = accounts.GetInfor(address.getId(), this.crypto);
			pakage.setInfor(inforP);
			
			AddressEntity entityP = addressS.GetAddress(address.getId());
			Address addressP = addressS.ConvertAddressEntityToAddress(entityP);
			pakage.setAddress(addressP);
			resp = ok(Json.toJson(pakage));
		}
		else
		{
			pakage.setType(0);
			resp = status(500, Json.toJson(pakage));
		}
		
		
		return CompletableFuture.completedFuture(resp);
	}
	
	public Result address(int id)
	{
		AddressEntity entity = new AddressEntity();
		entity = addressS.GetAddress(id);
		if(entity == null)
			entity = new AddressEntity();
		return ok(Json.toJson(entity));
	}
}
