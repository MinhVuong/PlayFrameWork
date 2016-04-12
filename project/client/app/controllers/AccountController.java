package controllers;

import models.Register;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import models.SecurityHelper;

public class AccountController extends Controller {

	private final Logger.ALogger log = Logger.of("AccountController");
	private SecurityHelper securitier = new SecurityHelper();
	public Result login()
	{
		log.info("Show Login");
		return ok(login.render(""));
	}
	public Result loginSubmit()
	{
		log.info("Login Submit");
		
		return ok(index.render(""));
	}
	public Result registerSubmit()
	{
		log.info("Register Submit");
		Register register = Form.form(Register.class).bindFromRequest().get();
		log.info(register.getEmail() + " " + register.getPassword() + " " + register.getGender() + " " + register.getFirstName() + " " + register.getLastName());
		
		return ok("true");
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
