package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.Category;
import models.CategoryProduct;
import entities.CategoryEntity;
import entities.CategoryProductEntity;
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

	public List<CategoryEntity> GetCategory()
	{

		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<CategoryEntity> result = (List<CategoryEntity>) em.createNativeQuery("Select * From category", CategoryEntity.class).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<CategoryEntity> GetCategory()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	public List<CategoryProductEntity> GetCategoryProductByIdCategory(int category)
	{

		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<CategoryProductEntity> result = (List<CategoryProductEntity>) em.createNativeQuery("Select * From category_product Where category_id = ?", CategoryProductEntity.class).setParameter(1, (int)category).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<CategoryEntity> GetCategory()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}

	public List<ProductEntity> GetProductByCategory(int category)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product Where category_product_id = ?", ProductEntity.class).setParameter(1, (int)category).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetProductByCategory(int category)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public String GetNameFromProductId(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryProductEntity result = (CategoryProductEntity)em.find(CategoryProductEntity.class, id);
			em.close();
			if(result != null)
				return result.getName();
			else
				return "";
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetNameFromProductId(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return "";
		}
	}
	
	public String GetNameFromCategoryId(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			CategoryEntity result = (CategoryEntity)em.find(CategoryEntity.class, id);
			em.close();
			if(result != null)
				return result.getName();
			else
				return "";
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetNameFromCategoryId(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return "";
		}
	}
	
	
	public List<Category> ChangeCategoryEntityToCategory(List<CategoryEntity> entity)
	{
		List<Category> categories = new ArrayList<Category>();
		int i=0;
		for(CategoryEntity cate : entity)
		{
			Category category = new Category();
			category.setId(cate.getId());
			category.setName(cate.getName());
			category.setNumberRow(i);
			i++;
			categories.add(category);
		}
		return categories;
	}
	public List<CategoryProduct> ChangeCategoryProductEntityToCategoryProduct(List<CategoryProductEntity> entity)
	{
		List<CategoryProduct> categories = new ArrayList<CategoryProduct>();
		int i=0;
		for(CategoryProductEntity cate : entity)
		{
			CategoryProduct category = new CategoryProduct();
			category.setId(cate.getId());
			category.setName(cate.getName());
			category.setNumberRow(i);
			category.setIdCategory(cate.getIdCategory());
			i++;
			categories.add(category);
		}
		return categories;
	}

	public ProductEntity GetProductById(int id)
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductEntity result = (ProductEntity)em.find(ProductEntity.class, id);
			em.close();
			if(result != null)
				return result;
			else
				return null;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("ProductEntity GetProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
}
