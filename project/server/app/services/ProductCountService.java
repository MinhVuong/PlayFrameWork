package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.CartProducts;
import models.ProductCart;
import entities.ProductCountEntity;
import entities.ExceptionEntity;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;

public class ProductCountService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	public int GetCountProductById(int id){
		
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			ProductCountEntity result = (ProductCountEntity) em.createNativeQuery("Select * From product_colors_stock  Where product_id=?", ProductCountEntity.class).setParameter(1, id).getSingleResult();
			em.close();
			
			if(result != null)
				return result.getCount();
			else
				return 0;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetCountProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public List<ProductCountEntity> GetListCountsProductById(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			List<ProductCountEntity> result = (List<ProductCountEntity>) em.createNativeQuery("Select * From product_colors_stock  Where product_id=?", ProductCountEntity.class).setParameter(1, id).getResultList();
			em.close();
			
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException(" GetListCountsProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public int CheckCountProductCart(CartProducts products)
	{
		int result = 0;
		for(ProductCart pro : products.getProducts())
		{
			int count = GetCountProductById(pro.getId());
			if(pro.getCount() > count)
				result = pro.getId();
		}
		return result;
	}
}
