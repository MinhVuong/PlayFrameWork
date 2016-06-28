package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.ExceptionEntity;
import entities.ProductInforEntity;

public class ProductInforService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	public List<ProductInforEntity> GetListInforProductByIdProduct(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductInforEntity> result = (List<ProductInforEntity>)em.createNativeQuery("select * from product_information where product_id=?", ProductInforEntity.class).setParameter(1, id).getResultList();
			
			em.close();
			if(result != null)
				return result;
			else
				return null;
			
		}catch(Exception e){
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductInforEntity> GetListInforProductByIdProduct(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
}
