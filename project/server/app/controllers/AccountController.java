package controllers;




import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import entities.AddressEntity;
import entities.CustomerEntity;
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
	private Logger.ALogger loger = Logger.of("accountController");
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
		loger.info("Submit Register");
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
			loger.info("Register Result: Didn't Register Account!");
			
			break;
			}
		case 1:{
			loger.info("Register Result: Register Successful");
			
			break;
			}
		case 2:{
			loger.info("Register Result: Email register had registed.");
			
			break;
			}
		case 3:{
			loger.info("Register Result: Didn't send email active account, so register fail!");
			break;
			}	
		}
		return ok(Json.toJson(pakage));
	}
	// Client active account
	public Result activeAccount(String token, Integer id)
	{
		if(accounts.ActiveAccount(token, id) == 1)
			return ok("true");
		else
			return ok("false");
	}
	// Login
	// Restul 1 if login success.
	// Result 0 if email didn't register.
	// Result -1 if password dot match.
	// Result -2 if account didn't active.
	public Result login()
	{
		loger.info("Client call Login!");
		Login login = new Login();
		JsonNode json = request().body().asJson();
		login = Json.fromJson(json, Login.class);
		
		// Kiểm tra dữ liệu client login.
		int result = accounts.Login(login, this.crypto);
		
		// Gói tin LoginPakage gửi vể cho Client.
		LoginPakage pakage = new LoginPakage();
		pakage.setType(result);
		if(result == 1)
		{
			User user = accounts.CreateUser(login);
			pakage.setUser(user);
			
		}
		else
			pakage.setUser(new User());
		return ok(Json.toJson(pakage));
	}

	// return 1 if change Password success.
	// return -1 if Password old not match.
	// return -2 if didn't exist customer from id
	// return 0 if didn't connect database.
	public Result changePass(){
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
		}
		return ok(Json.toJson(pakage));
	}
	
	// return 1 nếu Client update infor thành công.
	// return 0 nếu Client không kết nối được với Server.
	// return -1 nếu không tồn tại customer
	public Result updateInfor()
	{
		loger.info("Client update Infor!");
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
		}
		return ok(Json.toJson(pakage));
		
	}

	// Return 1 nếu thành công.
	// Return 0 nếu thất bại.
	public Result infor (String id)
	{
		// Infor lấy và gửi về cho client.
		// Password sẽ được giải mã lại cho client.
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
		}
		else
		{
			pakage.setType(0);
		}
		return ok(Json.toJson(pakage));
	}
	
	// Return 1 nếu thành công.
	// Return 0 nếu thất bại
	public Result updateAddress()
	{
		loger.info("Client update Address!");
		Address address = new Address();
		JsonNode json = request().body().asJson();
		address = Json.fromJson(json, Address.class);
		
		AddressEntity addressE = addressS.ConvertAddressToAddressEntity(address); 
		boolean result = addressS.UpdateAddress(address.getId(), addressE);
		InforPakage pakage = new InforPakage();
			
		if(result)
		{
			pakage.setType(1);
		}
		else
		{
			pakage.setType(0);
		}
		Infor inforP = accounts.GetInfor(address.getId(), this.crypto);
		pakage.setInfor(inforP);
		
		AddressEntity entityP = addressS.GetAddress(address.getId());
		Address addressP = addressS.ConvertAddressEntityToAddress(entityP);
		pakage.setAddress(addressP);
		return ok(Json.toJson(pakage));
	}
}
