package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import models.CartProducts;
import models.Category;
import models.CategoryProduct;
import models.ProductCountCate;
import entities.CategoryEntity;
import entities.CategoryProductEntity;
import entities.CustomerEntity;
import entities.ExceptionEntity;
import entities.ProductCountEntity;
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
		int start = (page -1)*10;
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product p, category_product c  Where p.is_active=1  AND(p.category_product_id = c.id_category_product and c.category_id=?) limit ?, ?", ProductEntity.class)
					.setParameter(1, (int)category).setParameter(2, (int)start).setParameter(3, (int)counts).getResultList();
			em.close();
			return result;
			
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
	//////////////////////////////  ADMIN  //////////////////
	
	public List<ProductEntity> GetProducts()
	{
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product", ProductEntity.class).getResultList();
			em.close();
			return result;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetAllProducts()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public boolean CheckExistProductById(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			ProductEntity result = em.find(ProductEntity.class, id);
			em.close();
			if(result != null)
				return true;
			else
				return false;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CheckExistProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public boolean CheckExistProductByName(String name){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			ProductEntity result = (ProductEntity)em.createNativeQuery("Select * From product where name=?", ProductEntity.class).setParameter(1, name).getSingleResult();
			if(result != null)
				return true;
			else
				return false;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CheckExistProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return false;
		}
	}
	
	public int AddProduct(ProductEntity entity){
		if(CheckExistProductByName(entity.getName()))
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
				ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddProduct(ProductEntity entity)", e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return 0;
			}
		}
	}
	
	public int DeleteProductById(int id){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductEntity result = em.find(ProductEntity.class, id);
			em.remove(result);
			em.getTransaction().commit();
			em.close();
			return 1;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("DeleteProductById(int id)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public int EditProduct(ProductEntity entity){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			ProductEntity result = em.find(ProductEntity.class, entity.getId());
			result.setCategoryId(entity.getCategoryId());
			result.setImage(entity.getImage());
			result.setIsActive(entity.getIsActive());
			result.setIsNew(entity.getIsNew());
			result.setIsWishlist(entity.getIsWishlist());
			result.setPriceSale(entity.getPriceSale());
			result.setName(entity.getName());
			
			
			em.getTransaction().commit();
			em.close();
			return 1;
			
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("AddProduct(ProductEntity entity)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return 0;
		}
	}
	
	public List<ProductCountCate> GetCateProductToColorAndCount(){
		try{
			List<ProductEntity> products = GetProducts();
			List<ProductCountCate> cates = new ArrayList<ProductCountCate>();
			for(ProductEntity p : products){
				ProductCountCate cate = new ProductCountCate(p.getId(), p.getName());
				cates.add(cate);
			}
			return cates;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductCountCate> GetCateProductToColorAndCount()", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductEntity> SearchPrice(float m1, float m2){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product where price between ? and ?", ProductEntity.class)
					.setParameter(1, m1).setParameter(2, m2).getResultList();
			if(result == null)
				result = new ArrayList<ProductEntity>();
			return result;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> SearchPrice(float m1, float m2))", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	public List<ProductEntity> SearchColor(String key){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductCountEntity> list = (List<ProductCountEntity>) em.createNativeQuery("Select * From product_colors_stock where key_color=?", ProductCountEntity.class)
					.setParameter(1, key).getResultList();
			List<ProductEntity> result = new ArrayList<ProductEntity>(); 
			for(ProductCountEntity i : list){
				ProductEntity pro = GetProductById(i.getProductId());
				result.add(pro);
			}
			return result;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> SearchColor(String key)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductEntity> SearchName(String name){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			name = name.toLowerCase();
			name = name.replace('_', ' ');
			name = "%"+name+"%";
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product where name like ?", ProductEntity.class)
					.setParameter(1, name).getResultList();
			if(result == null)
				result = new ArrayList<ProductEntity>();
			return result;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> SearchName(String name)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
	public List<ProductEntity> GetProductGood(int count){
		try{
			EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductEntity> result = (List<ProductEntity>) em.createNativeQuery("Select * From product  Where is_active=1 limit 0, ?", ProductEntity.class)
					.setParameter(1, count).getResultList();
			em.close();
			if(result == null){
				result = new ArrayList<ProductEntity>();
			}
			return result;
		}catch(Exception e)
		{
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("List<ProductEntity> GetProductGood(int count)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
			return null;
		}
	}
	
}
