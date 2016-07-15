package cache;

import java.util.List;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import entities.ProductEntity;
import play.Logger;
import play.cache.CacheApi;
import services.ProductService;

public class ProductCache {
	private ProductService productS = new ProductService();
	
	public List<ProductEntity> GetProductsByPage(int page, int count, int category, CacheApi cache){
		String key = "products_"+category + "_" + page + "_" + count;
		// Get From Cache
		List<ProductEntity> result = cache.get(key);
		
		if(result == null || result.size() == 0){
			// Get From DB
			result = productS.GetProductsByPage(page, count, category);			
			if(result.size() > 0){
				// Save into cache.
				cache.set(key, result);
			}
		}
		
		return result;
	}

	public boolean RemoveProductByPage(int page, int count, int category, CacheApi cache){
		try{
			String key = "products_"+category + "_" + page + "_" + count;
			cache.remove(key);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public int PageTotal(int category, int count, CacheApi cache){
		String value="";
		int page = 0;
		String key = "page_"+category+"_"+count;
		value = (String)cache.get(key);
		if(value == null || value.equals("")){
			if(page <= 0){
				page = productS.PageTotal(category, count);
				
				value = Integer.toString(page);
				cache.set(key, value);			
			}
		}else{
			page = Integer.parseInt(value);
			
		}		
		return page;
	}
	public void RemovePageTotal(int category, int count, CacheApi cache){
		String key = "page_"+category+"_"+count;
		cache.remove(key);
		
	}
	
	public List<CategoryEntity> GetCategory(CacheApi cache){
		String key = "category";
		List<CategoryEntity> result = (List<CategoryEntity>)cache.get(key);
		if(result == null || result.size() == 0){
			result = productS.GetCategory();
			cache.set(key, result);
		}
		return result;
	}
	public void RemoveGetCategory(CacheApi cache){
		String key = "category";
		cache.remove(key);
	}
	
	public List<CategoryProductEntity> GetCategoryProductByIdCategory(int category, CacheApi cache){
		String key = "categoryProduct_"+category;
		List<CategoryProductEntity> result = cache.get(key);
		if(result == null || result.size() == 0){
			result = productS.GetCategoryProductByIdCategory(category);
			cache.set(key, result);
		}
		return result;
	}
	public void RemoveCategoryProductByIdCategory(int category,CacheApi cache){
		String key = "categoryProduct_"+category;
		cache.remove(key);
	}
}
