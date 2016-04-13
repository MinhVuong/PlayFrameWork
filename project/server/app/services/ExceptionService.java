package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.EntityManagerFactorySingleton;
import entities.ExceptionEntity;

public class ExceptionService {

	public ExceptionService() {
		super();
	}
	
	// Add exception into database
	public void AddException(ExceptionEntity exception)
	{
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(exception);
		em.getTransaction().commit();
		em.close();
	}

	
}
