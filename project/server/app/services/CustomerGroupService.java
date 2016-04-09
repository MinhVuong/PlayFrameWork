package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.CustomerGroup;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;

@Transactional
@Singleton
public class CustomerGroupService {

	private JPAApi jpaApi;
	@Inject
	public CustomerGroupService(JPAApi api) {
	    this.jpaApi = api;
	}
	
	public CustomerGroupService() {
	    
	}
	
	@Transactional()
	public List<CustomerGroup> getAccounts()
	{ 
		
		ArrayList<CustomerGroup> list = new ArrayList<CustomerGroup>();
		jpaApi.withTransaction(() -> {
			EntityManager em = jpaApi.em();
		    Query query = em.createNativeQuery("select u from CustomerGroup u");
		    List<CustomerGroup> groups = (List<CustomerGroup>) query.getResultList();
		    for(CustomerGroup cus : groups)
		    {
		    	list.add(cus);
		    }
		});
		return list;
	}
}
