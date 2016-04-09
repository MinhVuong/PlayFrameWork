package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit"); 
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    List<CustomerGroup> groups = (List<CustomerGroup>)em.createQuery("select u from CustomerGroup u").getResultList();
	    em.close();
	    emf.close();
	    return groups;
		
	}
}
