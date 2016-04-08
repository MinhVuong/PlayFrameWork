package services;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.db.jpa.JPAApi;

public class AccountService {

	private JPAApi jpaApi;
	@Inject
	public AccountService(JPAApi api) {
	    this.jpaApi = api;
	}
	
	public AccountService() {
	    
	}
	
	public Long getAccoutn()
	{
		 EntityManager em = jpaApi.em();
		 Query query = em.createNativeQuery("select count(id_customer_group) from customer_group");
		 return (Long) query.getSingleResult();
	}
}
