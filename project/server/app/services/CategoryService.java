package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.CategoryEntity;
import entities.ExceptionEntity;

public class CategoryService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public CategoryService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<CategoryEntity> GetList(){
		
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			List<CategoryEntity> result = (List<CategoryEntity>)em.createNativeQuery("Select * From category", CategoryEntity.class).getResultList();
			em.close();
			
			if(result != null)
				return result;
			else
				return null;
			
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("List<CategoryEntity> GetList()",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public boolean CheckExists(CategoryEntity entity){
		
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryEntity result = (CategoryEntity)em.createNativeQuery("Select * From category where name_category=?", CategoryEntity.class)
					.setParameter(1, entity.getName()).getSingleResult();
			em.close();
			if(result != null)
				return true;
			else
				return false;
			
						
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("CheckExists(CategoryEntity entity)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public int AddCategory(CategoryEntity entity){
		if(CheckExists(entity))
			return -1;
		else{
			try {
				EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(entity);
				em.getTransaction().commit();
				em.close();
				return 1;			
			}catch(Exception e){
				ExceptionEntity exceptionEntity = exceptionHelper
						.createExceptionEntityFromException("AddCategory(CategoryEntity entity)",e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return 0;
			}
		}
	}
	
	public int EditCategory(CategoryEntity entity){
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryEntity result = (CategoryEntity)em.find(CategoryEntity.class, entity.getId());
			result.setName(entity.getName());
			em.getTransaction().commit();
			em.close();
			return 1;			
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("EditCategory(CategoryEntity entity)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public CategoryEntity GetCategoryById(int id){
		
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryEntity result = em.find(CategoryEntity.class, id);
			if(result != null)
				return result;
			else
				return null;						
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("CategoryEntity GetCategoryById(int id){",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public boolean DeleteCategory(int id){
		
		try {
			
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryEntity entity = em.find(CategoryEntity.class, id);
			em.remove(entity);
			em.getTransaction().commit();
			em.close();
			return true;
			
						
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("DeleteCategory(CategoryEntity entity)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}

}
