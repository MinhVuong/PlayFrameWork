package controllers;

import helper.SessionHelper;
import models.User;
import play.mvc.*;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	private SessionHelper sessionH = new SessionHelper();
    public Result index() {
    	User user = new User();
    	user = sessionH.GetUser("user");
    	if(user.getId() == 0){
    		return ok(login.render("",user));
    	}else{
    		return ok(index.render(user));
        }
    }

}
