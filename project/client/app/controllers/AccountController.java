package controllers;

import play.mvc.*;
import views.html.*;

public class AccountController extends Controller {

	public Result login()
	{
		return ok(login.render(""));
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
