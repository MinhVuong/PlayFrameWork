package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.CustomerEntity;
import entities.ExceptionEntity;
import entities.ProductEntity;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;

public class ProductService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	
	public List<ProductEntity> GetAllProducts()
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product  Where is_active=1", ProductEntity.class).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductEntity> GetProductsByPage(int page, int counts)
	{
		int start = counts * (page - 1);
		int end = counts * page;
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product  Where is_active=1 AND (id_product BETWEEN ? AND ?)", ProductEntity.class).setParameter(1, (int)start).setParameter(2, (int)end).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductEntity> GetProductsByPage(int page, int counts, int category){
		int start = counts * (page - 1);
		int end = counts * page;
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product p, category_product c  Where p.is_active=1  AND(p.category_product_id = c.id_category_product and c.category_id=?)", ProductEntity.class).setParameter(1, (int)category).getResultList();
			em.close();
			ArrayList<ProductEntity> result1 = new ArrayList<ProductEntity>();
			for(int i = start; i< end; i++)
			{
				result1.add(result.get(i));
			}
			return result1;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}

	public int PageTotal(int category, int count)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product p, category_product c  Where p.is_active=1  AND(p.category_product_id = c.id_category_product and c.category_id=?)", ProductEntity.class).setParameter(1, (int)category).getResultList();
			em.close();
			int page = result.size()/10;
			int pageD = result.size()%10;
			if(pageD > 0)
				page ++;
			return page;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return -1;
		}
	}
}
