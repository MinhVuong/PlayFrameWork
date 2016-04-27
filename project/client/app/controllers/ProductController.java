package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ProductController extends Controller{

	public Result cart()
	{
		
		return ok(cart.render(""));
	}
}
