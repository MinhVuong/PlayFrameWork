package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.CartEntity;
import entities.ExceptionEntity;
import business.DateHelper;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import models.CartProducts;
import models.ProductCart;

public class CartService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public CartService() {
	    
	}
	
	public boolean AddCart(CartEntity entity)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			em.close();
			return true;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddCart(CartEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public CartEntity CreateCartEntity(CartProducts products, int id)
	{
		CartEntity cart = new CartEntity();
		cart.setId(id);
		
		String ids = "";
		String names = "";
		String counts = "";
		String prices = "";
		int count_Total=0;
		float price_Total = 0f;
		for(ProductCart pro : products.getProducts())
		{
			ids += Integer.toString(pro.getId());
			ids += ";";
			
			names += pro.getName();
			names += ";";
			
			counts += Integer.toString(pro.getCount());
			counts += ";";
			
			prices += Float.toString(pro.getPrice());
			prices += ";";
			
			count_Total += pro.getCount();
			price_Total += pro.getPrice();
		}
		cart.setCountTotal(count_Total);
		cart.setPriceTotal(price_Total);
		cart.setProductId(ids);
		cart.setProductName(names);
		cart.setProductCount(counts);
		cart.setProductPrice(prices);
		DateHelper date = new DateHelper();
		cart.setCreateAt(date.getDateTimeCurrent());
		
		return cart;
	}
}
