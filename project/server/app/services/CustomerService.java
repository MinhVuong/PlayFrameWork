package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import pakageResult.User;
import play.api.libs.Crypto;
import play.libs.mailer.MailerClient;
import business.DateHelper;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import business.MailHelper;
import business.MyCrypto;
import business.TokenHelper;
import entities.CustomerEntity;
import entities.ExceptionEntity;
import entities.TokenEntity;
import models.ChangePass;
import models.Infor;
import models.Login;


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
	
	// Get Customer By Email
	public CustomerEntity GetCustomerByEmail(String email)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity result = (CustomerEntity) em.createNativeQuery("Select * From customer  Where email=?", CustomerEntity.class).setParameter(1, email).getSingleResult();
			em.close();
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetCustomerByEmail(String email)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	// Login
	// Restul 1 if login success
	// Result 0 if email didn't register.
	// Result -1 if password dot match.
	// Result -2 if account didn't active.
	public int Login(Login login, Crypto crypt)
	{
		CustomerEntity customer = GetCustomerByEmail(login.getEmail());
		if(customer != null)
		{
			MyCrypto myCrypt = new MyCrypto(crypt);
			if(myCrypt.decrypt(customer.getPassword()).equals(login.getPassword()))
			{
				if(customer.getIsActive() == 0)
					return -2;
				else
				{
					// Login success then increase number_log
					IncreaseNumberLog(customer);
					return 1;
				}
			}else
			return -1;
		}else		
		return 0;
	}
	
	// Create User to response Client after login success
	public User CreateUser(Login login)
	{
		User user = new User();
		CustomerEntity customer = GetCustomerByEmail(login.getEmail());
		user.setId(customer.getId());
		user.setEmail(login.getEmail());
		user.setFirstName(customer.getFirstName());
		user.setToken("");
		return user;
	}
	// Increase Number Log of Customer after login success.
	public boolean IncreaseNumberLog(CustomerEntity customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity entity = em.find(CustomerEntity.class, customer.getId());
			int number_log = entity.getNumberLog();
			number_log ++;
			entity.setNumberLog(number_log);
			em.getTransaction().commit();
			em.close();
			return true;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("IncreaseNumberLog(CustomerEntity customer)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	// return 1 if change Password success.
	// return -1 if Password old not match.
	// return -2 if didn't exist customer from id
	// return 0 if didn't connect database.
	public int ChangePassword (ChangePass changePass, Crypto crypt)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity customer = em.find(CustomerEntity.class, changePass.getId());
			if(customer != null){
				MyCrypto myCrypt = new MyCrypto(crypt);
				if(myCrypt.decrypt(customer.getPassword()).equals(changePass.getPassOld())){
					customer.setPassword(myCrypt.encrypt(changePass.getPassNew()));
					DateHelper dateHelper = new DateHelper();
					customer.setUpdateAt(dateHelper.getDateTimeCurrent());
					em.getTransaction().commit();
					em.close();
					return 1;
				}else{
					em.close();
					return -1;
				}
			}else{
				em.close();
				return -2;
			}
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("ChangePassword (int id, String oldPass, String newPass)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
		
	}
	
	public boolean UpdateCustomer(CustomerEntity customer){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity result = em.find(CustomerEntity.class, customer.getId());
			result = customer;
			em.getTransaction().commit();
			em.close();
			return true;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetCustomerFromID(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	// Result 1 if success.
	// Result 0 if didn't connect to database.
	// Result -1 if didn't exist customer.
	public int UpdateInfor(Infor infor)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity customer = em.find(CustomerEntity.class, infor.getId());
			if(customer != null){
				customer.setFirstName(infor.getFirstName());
				customer.setLastName(infor.getLastName());
				customer.setGender(infor.getGender());
				
				DateHelper dateHelper = new DateHelper();
				customer.setUpdateAt(dateHelper.getDateTimeCurrent());
				
				em.getTransaction().commit();
				em.close();
				return 1;
			}else{
				em.close();
				return -1;
			}
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("UpdateInfor(Infor infor)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public Infor GetInfor(int id, Crypto crypt)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CustomerEntity customer = em.find(CustomerEntity.class, id);
			if(customer != null){
				Infor infor = new Infor();
				infor.setId(id);
				infor.setEmail(customer.getEmail());
				infor.setGender(customer.getGender());
				infor.setFirstName(customer.getFirstName());
				infor.setLastName(customer.getLastName());
				MyCrypto myCrypt = new MyCrypto(crypt);
				infor.setPassword(myCrypt.decrypt(customer.getPassword()));
				em.close();
				
				return infor;
			}else{
				em.close();
				return null;
			}
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetInfor(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
}
