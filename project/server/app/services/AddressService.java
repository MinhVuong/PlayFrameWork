package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.Address;
import models.Infor;
import entities.AddressEntity;
import entities.CustomerEntity;
import entities.ExceptionEntity;
import business.DateHelper;
import business.EntityManagerFactorySingleton;
import business.ExceptionHelper;

public class AddressService {
	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	public AddressService() {
	    
	}
	
	public boolean AddAddress(AddressEntity entity)
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
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddAddress(AddressEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	public boolean CheckExistAddress(int id_Customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			AddressEntity address = (AddressEntity)em.createNativeQuery("Select * From customer_address  Where customer_id=?", AddressEntity.class).setParameter(1, id_Customer).getSingleResult();
			if(address != null){
				return true;
				
			}else{
				return false;
				
			}
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CheckExistAddress(int id_Customer)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	// Return true nếu update thành công.
	// Return false nếu thất bại.
	public boolean UpdateAddress(int id_Customer, AddressEntity entity)
	{
		if(CheckExistAddress(id_Customer))
		{
			try{
				EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				AddressEntity address = (AddressEntity)em.createNativeQuery("Select * From customer_address  Where customer_id=?", AddressEntity.class).setParameter(1, id_Customer).getSingleResult();
				if(address != null){
					address.setFirstName(entity.getFirstName());
					address.setLastName(entity.getLastName());
					address.setPhone(entity.getPhone());
					address.setStreet(entity.getStreet());
					address.setCity(entity.getCity());
					DateHelper dateHelper = new DateHelper();
					address.setUpdateAt(dateHelper.getDateTimeCurrent());
					em.getTransaction().commit();
					em.close();
					return true;
				}else{
					
					em.close();
					return false;
				}
				
				
			}catch(Exception e)
			{
				ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("UpdateAddress(int id)", e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return false;
			}
		}else
		{
			return AddAddress(entity);
		}
	}
	
	public AddressEntity GetAddress(int id_Customer)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			AddressEntity address = (AddressEntity)em.createNativeQuery("Select * From customer_address  Where customer_id=?", AddressEntity.class).setParameter(1, id_Customer).getSingleResult();
			if(address != null){
				em.close();
				return address;
			}else{
				em.close();
				return null;
			}
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetAddress(int id_Customer)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public Address ConvertAddressEntityToAddress(AddressEntity entity)
	{
		Address address = new Address();
		address.setId(entity.getCustomer_id());
		address.setFirstName(entity.getFirstName());
		address.setLastName(entity.getLastName());
		address.setPhone(entity.getPhone());
		address.setStreet(entity.getStreet());
		address.setCity(entity.getCity());
		return address;
	}
	
	public AddressEntity ConvertAddressToAddressEntity(Address address)
	{
		AddressEntity entity = new AddressEntity();
		entity.setCustomer_id(address.getId());
		entity.setFirstName(address.getFirstName());
		entity.setLastName(address.getLastName());
		entity.setPhone(address.getPhone());
		entity.setStreet(address.getStreet());
		entity.setCity(address.getCity());
		return entity;
	}
}
