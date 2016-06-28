package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.CartProducts;
import models.ProductCart;
import models.ProductCountShow;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.ExceptionEntity;
import entities.ProductCountEntity;
import entities.ProductEntity;

public class ProductCountService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	private ProductService productS = new ProductService();
	public int GetCountProductById(int id, String color){
		
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			ProductCountEntity result = (ProductCountEntity) em.createNativeQuery("Select * From product_colors_stock  Where product_id=? and key_color=?", ProductCountEntity.class).setParameter(1, id).setParameter(2, color).getSingleResult();
			em.close();
			
			if(result != null)
				return result.getCount();
			else
				return -1;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetCountProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return -2;
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
			int count = GetCountProductById(pro.getId(), pro.getColor());
			if(pro.getCount() > count)
				result = pro.getId();
		}
		return result;
	}
	
	
	public List<ProductCountEntity> GetList(){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			List<ProductCountEntity> result = (List<ProductCountEntity>) em.createNativeQuery("Select * From product_colors_stock", ProductCountEntity.class).getResultList();
			em.close();
			
			if(result != null)
				return result;
			else
				return null;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductCountEntity> GetList()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductCountShow> GetListShow(){
		try{
			List<ProductCountEntity> colors = GetList();
			List<ProductCountShow> list = new ArrayList<ProductCountShow>();
			for(ProductCountEntity e : colors){
				ProductEntity en = productS.GetProductById(e.getId());
				ProductCountShow c = new ProductCountShow();
				c.ConvertFromProductCountEntity(e, en.getName());
				
				list.add(c);
			}
			
			return list;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductCountShow> GetListShow()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	public boolean CheckExistProductCount(ProductCountEntity entity){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductCountEntity result = (ProductCountEntity) em.createNativeQuery("Select * From product_colors_stock  Where product_id=? and key_color=? and value_color=?", ProductCountEntity.class)
					.setParameter(1, entity.getProductId()).setParameter(2, entity.getKey()).setParameter(3, entity.getValue()).getSingleResult();
			em.close();
			if(result != null)
				return true;
			else
				return false;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CheckExistProductCount(ProductCountEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public int UpdateProductCount(ProductCountEntity entity){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductCountEntity result = (ProductCountEntity) em.createNativeQuery("Select * From product_colors_stock  Where id_product_color_stock=?", ProductCountEntity.class).setParameter(1, entity.getId()).getSingleResult();
			result.setCount(entity.getCount());
			result.setKey(entity.getKey());
			result.setPrice(entity.getPrice());
			result.setProductId(entity.getProductId());
			result.setValue(entity.getValue());
			
			em.getTransaction().commit();
			em.close();
			
			return 1;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("UpdateProductCount(ProductCountEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public int DeleteProductCount(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductCountEntity result = (ProductCountEntity) em.createNativeQuery("Select * From product_colors_stock  Where id_product_color_stock=?", ProductCountEntity.class).setParameter(1, id).getSingleResult();
			em.remove(result);			
			em.getTransaction().commit();
			em.close();
			
			return 1;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("DeleteProductCount(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public int AddProductCount(ProductCountEntity entity){
		if(CheckExistProductCount(entity))
			return -1;
		else{
			try{
				EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.persist(entity);
				em.getTransaction().commit();
				em.close();
				return 1;
			}catch(Exception e)
			{
				ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddProductCount(ProductCountEntity entity)", e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return 0;
			}
		}
		
	}
}
