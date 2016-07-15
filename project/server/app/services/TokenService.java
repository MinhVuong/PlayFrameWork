package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;




import entities.CustomerEntity;
import entities.ExceptionEntity;
import entities.TokenEntity;
import business.DateHelper;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;


public class TokenService {

	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	public TokenService(){}
	
	// add Token to database
	public boolean AddToken(TokenEntity entity)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();			
			em.close();
			
			return true;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddToken(TokenEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	// Result 1 if success
	// Result 0 if fail or token fail
	// Result -1 if time > 24 hours
	public int VerifyToken(String token, String start_time)
	{

		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TokenEntity tokenEntity = (TokenEntity)em.createNativeQuery("Select * From token  Where token=?", TokenEntity.class).setParameter(1, token).getSingleResult();
			em.close();
			if(tokenEntity != null)
			{
				String stop_time =  tokenEntity.getExpiryDate();
				DateHelper dateHelper = new DateHelper();
				int count = dateHelper.CalculationBetweenTwoTime(stop_time, start_time);
				if(count >= 0)
					return 1;
				else
					return -1;
			}
			else
				return 0;
			
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddToken(TokenEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
		
	}
	
	public boolean DeleteToken(String token)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TokenEntity tokenEntity = (TokenEntity)em.createNativeQuery("Select * From token  Where token=?", TokenEntity.class).setParameter(1, token).getSingleResult();
			em.remove(tokenEntity);
			em.getTransaction().commit();
			em.close();
			return true;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddToken(TokenEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public TokenEntity GetTokenFormTokenString(String token)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TokenEntity tokenEntity = (TokenEntity)em.createNativeQuery("Select * From token  Where token=?", TokenEntity.class).setParameter(1, token).getSingleResult();
			em.close();
			if(tokenEntity != null)
				return tokenEntity;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddToken(TokenEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public TokenEntity FindTokenFormTokenString(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TokenEntity tokenEntity = (TokenEntity)em.createNativeQuery("Select * From token  Where customer_id=?", TokenEntity.class).setParameter(1, id).getSingleResult();
			em.close();
			if(tokenEntity != null)
				return tokenEntity;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddToken(TokenEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	
}
