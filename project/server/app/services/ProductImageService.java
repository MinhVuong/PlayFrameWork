package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.ExceptionEntity;
import entities.ProductImageEntity;

public class ProductImageService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public List<ProductImageEntity> GetListImageProductBuIdProduct(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductImageEntity> result = (List<ProductImageEntity>)em.createNativeQuery("select * from product_pictues where product_id=?", ProductImageEntity.class).setParameter(1, id).getResultList();
			em.close();
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductImageEntity> GetListImageProductBuIdProduct(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
}
