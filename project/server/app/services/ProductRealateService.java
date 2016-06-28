package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.ExceptionEntity;
import entities.ProductRelateEntity;

public class ProductRealateService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	public List<ProductRelateEntity> GetProductRelateByProductId(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductRelateEntity> result = (List<ProductRelateEntity>)em.createNativeQuery("select * from product_relation where product_id=?", ProductRelateEntity.class).setParameter(1, id).getResultList();
			em.close();
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
}
