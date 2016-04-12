package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import play.libs.mailer.MailerClient;
import business.EntityManagerFactorySingleton;
import business.MailHelper;
import entities.CustomerEntity;


public class CustomerService {
	
	 
	public CustomerService() {
	    
	}
	
	// Insert Record Object CustomerEntity into table customer.
	// return 1 if success OR return 0 if fail.
	// return 2 if exist CustomerEntity in table customer.
	// return 3 if didn't send email Customer
	public int AddCustomer(CustomerEntity customer, MailerClient mailerClient)
	{
		
		if(!CheckCustomer(customer))
		{
			MailHelper mail = new MailHelper(mailerClient);
			if(mail.SendMailActiveAccount(customer.getEmail())==true)
			{
				try{
					EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					em.persist(customer);
					em.getTransaction().commit();
					em.close();
					
					return 1;
				}catch(Exception e)
				{
					return 0;
				}
			}
			else
				return 3;
			
		}
		else
			return 2;
	}
	
	// Are Object customer exist in database?
	// Result true if exist customer in database.
	public boolean CheckCustomer(CustomerEntity customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			CustomerEntity result = (CustomerEntity) em.createNativeQuery("Select * From customer  Where email=?", CustomerEntity.class).setParameter(1, customer.getEmail()).getSingleResult();
			
			em.close();
			
			return true;
		}catch(Exception e)
		{
			return false;
		}
		
	}
	
	
	
}
