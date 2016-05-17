package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import play.api.libs.Crypto;
import entities.CustomerEntity;
import entities.ExceptionEntity;
import models.Login;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import business.MyCrypto;

public class ACustomerService {

	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public ACustomerService() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public int CheckLoginAdmin(Login login, Crypto crypt){
		
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			CustomerEntity result = (CustomerEntity) em.createNativeQuery(
							"Select * From customer  Where email=? and group_id=2",
							CustomerEntity.class).setParameter(1, login.getEmail()).getSingleResult();
			em.close();

			if (result != null){
				MyCrypto myCrypt = new MyCrypto(crypt);
				if(myCrypt.decrypt(result.getPassword()).equals(login.getPassword())){
					return 1;
				}else{
					return 2;
				}
			}
			else
				return 0;
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("CheckLoginAdmin(Login login, Crypto crypt)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public int GetRoleAdmin(Login login,  Crypto crypt)
	{
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			CustomerEntity result = (CustomerEntity) em.createNativeQuery(
							"Select * From customer  Where email=? and is_active=1",
							CustomerEntity.class).setParameter(1, login.getEmail()).getSingleResult();
			em.close();

			if (result != null){
				MyCrypto myCrypt = new MyCrypto(crypt);
				if(myCrypt.decrypt(result.getPassword()).equals(login.getPassword())){
					return result.getGroupId();
				}else{
					return 0;
				}
			}
			else
				return 0;
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("GetRoleAdmin(Login login,  Crypto crypt)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
}
