package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;




import play.libs.mailer.MailerClient;
import business.DateHelper;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import business.MailHelper;
import business.TokenHelper;
import entities.CustomerEntity;
import entities.ExceptionEntity;
import entities.TokenEntity;


public class CustomerService {
	
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();

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
			
				try{
					// Add Customer into database
					EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					em.persist(customer);
					em.getTransaction().commit();
					em.close();
					
					MailHelper mail = new MailHelper(mailerClient);
					int id = GetIdCustomer(customer);
					// Generate Token to add Token into database
					TokenEntity tokenEntity = new TokenEntity();
					TokenHelper tokenHelper = new TokenHelper();
					tokenEntity = tokenHelper.CreateTokenForCustomerID_EMAIL(id);
					String token = tokenEntity.getToken();
					TokenService tokenService = new TokenService();
					tokenService.AddToken(tokenEntity);
					// Send mail to active Account
					if(mail.SendMailActiveAccount(id,customer.getFirstName(), customer.getEmail(), token)==true)
						return 1;
					else
					{
						// If didn't send email, You must delete Customer had insert.
						DeleteCustomer(customer);
						return 3;
					}
				}catch(Exception e)
				{
					ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddCustomerAddCustomer(CustomerEntity customer, MailerClient mailerClient)", e.getMessage());
					exceptionService.AddException(exceptionEntity);
					return 0;
				}
			
			
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
			
			if(result != null)
				return true;
			else
				return false;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CheckCustomer(CustomerEntity customer)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
		
	}
	
	// Do you delete object customer exist in database?
	// Result true if delete success.
	public boolean DeleteCustomer(CustomerEntity customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			em.remove(customer);
			em.getTransaction().commit();
			
			em.close();
			return true;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("Delete(CustomerEntity customer)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
		
	}
	
	// Active Account After Register successful.
	// Result 1 if active successful.
	// Result -1 if time active > 24 hours ==> fail.
	// Result 0 if didn't connect database.
	
	public int ActiveAccount(String token, Integer id)
	{
		DateHelper dateHelper = new DateHelper();
		String time = dateHelper.getDateTimeCurrent();
		TokenService tokenService = new TokenService();
		// Verify token and time verified
		int result_token = tokenService.VerifyToken(token, time);
		switch(result_token)
		{
		case 1:	// verify success
			{
				try{
					// Edit account active = 1
					EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					CustomerEntity customer = em.find(CustomerEntity.class, id);				
					customer.setIsActive(1);
					em.getTransaction().commit();
					em.close();
					return 1;
					
				}catch(Exception e)
				{
					ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("ActiveAccount(Integer id)", e.getMessage());
					exceptionService.AddException(exceptionEntity);
					return 0;
				}
			}
			case -1:		// time fail
			{
				// Delete Token
				TokenEntity tokenEntity = new TokenEntity();
				TokenService tokenS = new TokenService();
				tokenS.DeleteToken(token);
				// Delete Customer Register
				CustomerEntity customer = GetCustomerFromID(id);
				DeleteCustomer(customer);
				
				return -1;
			}
			case 0:			// didn't connect database or token fail
			{
				return 0;
			}
		}
		return 0;
		
	}
	
	// Result id of customer in database;
	public Integer GetIdCustomer(CustomerEntity customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity result = (CustomerEntity) em.createNativeQuery("Select * From customer  Where email=?", CustomerEntity.class).setParameter(1, customer.getEmail()).getSingleResult();
			em.close();
			if(result != null)
				return result.getId();
			else
				return 0;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("ActiveAccount(Integer id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	// Get Customer From ID
	public CustomerEntity GetCustomerFromID(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity result = (CustomerEntity) em.createNativeQuery("Select * From customer  Where id_customer=?", CustomerEntity.class).setParameter(1, id).getSingleResult();
			em.close();
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetCustomerFromID(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
}
