package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CustomerGroupService;
import models.CustomerGroup;


public class ProductController extends Controller {

	private CustomerGroupService groups = new CustomerGroupService();
	
	
	public Result login()
	{
		List<CustomerGroup> list = groups.getAccounts();
		return ok(Json.toJson(list));
	}
}
