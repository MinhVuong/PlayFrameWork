package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.ProductGood;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;
import entities.ExceptionEntity;
import entities.OrderEntity;

public class OrderService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	private ProductService productS = new ProductService();
	
	public OrderService(){
		
	}
	
	public int AddOrder(OrderEntity entity){
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
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddOrder(OrderEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public List<OrderEntity> GetList(){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<OrderEntity> result = (List<OrderEntity>)em.createNativeQuery("select * from orders", OrderEntity.class).getResultList();
			em.close();
			if(result != null)
				return result;
			else
				return null;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<OrderEntity> GetList()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductGood> GetIdProductGood(){
		List<OrderEntity> list = GetList();
		if(list == null){
			return null;
		}else{
			List<ProductGood> result = new ArrayList<ProductGood>();
			for(OrderEntity or : list){
				String ids = or.getIds();
				String counts = or.getCounts();
				String[] ida = ids.split(";");
				String[] counta = counts.split(";");
				for(int i = 0; i<ida.length; i++){
					if(!("".equals(ida[i]))){
						ProductGood p = new ProductGood(Integer.parseInt(ida[i]), Integer.parseInt(counta[i]));
						if(result.size() < 10){
							result = AddAndSort(result, p);
						}
					}
					
				}
			}
			result = SortListProductGood(result);
			return result;
		}
	}
	
	public List<ProductGood> AddAndSort(List<ProductGood> list, ProductGood p){
		int tag = -1;
		List<ProductGood> result = list;
		for(int i=0; i<list.size(); i++){
			ProductGood pg = list.get(i);
			if(pg.getId() == p.getId())
			{
				result.get(i).setCount(pg.getCount() + p.getCount());
				tag = 1;
				break;
			}
		}
		if(tag == -1)
			result.add(p);
			
		return result;
	}
	
	public List<ProductGood> SortListProductGood(List<ProductGood> list){
		//List<ProductGood> result = list;
		int size = list.size();
		ProductGood a = new ProductGood();
		for(int i=0; i<size-1; i++){
			for(int j = i+1; j< size; j++){
				if(list.get(i).getCount() < list.get(j).getCount()){
					a = list.get(i);
					list.set(i, list.get(j));
					list.set(j, a);
				}
			}
		}
		
		return list;
	}
	
	public int UpdateEntity(int id, int status){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			OrderEntity result = em.find(OrderEntity.class, id);
			if(result != null){
				result.setStatus(status);
			}
			em.getTransaction().commit();
			em.close();
			return 1;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("UpdateEntity(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	public OrderEntity GetOrderById(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			OrderEntity result = em.find(OrderEntity.class, id);
			em.close();
			if(result != null){
				return result;
			}
			return null;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("OrderEntity GetOrderById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	
	
	
}
