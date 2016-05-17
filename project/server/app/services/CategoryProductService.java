package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.CategoryProductEntity;
import entities.ExceptionEntity;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;

public class CategoryProductService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public CategoryProductService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<CategoryProductEntity> GetCategoryProductList(){
		
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<CategoryProductEntity> result = (List<CategoryProductEntity>)em.createNativeQuery("select * from category_product", CategoryProductEntity.class).getResultList();
			
			em.close();
			return result;
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("List<CategoryProductEntity> GetCategoryProductList()",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public boolean CheckExist(String name, int cate)
	{
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryProductEntity result = (CategoryProductEntity)em.createNativeQuery("select * from category_product where name_category_product=? and category_id=?", CategoryProductEntity.class)
					.setParameter(1, name).setParameter(2, cate).getSingleResult();
			em.close();
			if(result != null)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException("CheckExist(CategoryProductEntity id)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	public int Add(CategoryProductEntity entity)
	{
		if(CheckExist(entity.getName(), entity.getIdCategory()))
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
				
			} catch (Exception e) {
				ExceptionEntity exceptionEntity = exceptionHelper
						.createExceptionEntityFromException("Add(CategoryProductEntity entity)",e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return 0;
			}
		}
		
	}
	
	public int Update(CategoryProductEntity entity)
	{
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryProductEntity result = em.find(CategoryProductEntity.class, entity.getId());
			result.setName(entity.getName());
			result.setIdCategory(entity.getIdCategory());
			em.getTransaction().commit();
			em.close();
			return 1;
			
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException(" Update(CategoryProductEntity entity)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public int Delete(int id)
	{
		try {
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryProductEntity result = em.find(CategoryProductEntity.class, id);
			em.remove(result);
			em.getTransaction().commit();
			em.close();
			return 1;
			
		} catch (Exception e) {
			ExceptionEntity exceptionEntity = exceptionHelper
					.createExceptionEntityFromException(" Delete(CategoryProductEntity entity)",e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
}
