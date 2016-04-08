package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.CustomerGroup;

@Transactional
@Singleton
public class ProductController extends Controller {

	private JPAApi jpaApi;
	@Inject
	public ProductController(JPAApi api) {
	    this.jpaApi = api;
	}
	
	@Transactional()
	public Result login()
	{
		
		 List<CustomerGroup> groups = (List<CustomerGroup>) jpaApi.em().createQuery("select u from CustomerGroup u", CustomerGroup.class).getResultList();
		 return ok(Json.toJson(groups));
	}
}
