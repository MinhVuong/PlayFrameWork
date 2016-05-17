package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.ExceptionEntity;
import adminEntities.AdminRoleEntity;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;

public class AdminRoleService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public AdminRoleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<AdminRoleEntity> GetListRuleByIdAdmin(int id)
	{
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<AdminRoleEntity> result = (List<AdminRoleEntity>)em.createNativeQuery("Select * From admin_roles  Where group_id=?",AdminRoleEntity.class).setParameter(1, id).getResultList();			
			em.close();
			if(result != null)
				return result;
			else
				return null;
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("List<AdminRoleEntity> GetListRuleByIdAdmin(int id)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
}
