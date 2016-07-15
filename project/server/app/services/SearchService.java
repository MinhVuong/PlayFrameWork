package services;

import java.util.ArrayList;
import java.util.List;

import play.Logger;
import entities.ProductEntity;
import business.ExceptionHelper;

public class SearchService {
	private Logger.ALogger logger = Logger.of("search");
	private ProductService productS = new ProductService();
	
	public List<ProductEntity> Search(int cate, String key){
		List<ProductEntity> result = new ArrayList<ProductEntity>();
		switch(cate){
			case 1:{
				float a = 0,b = 0;
				if(key.equals("1")){
					a = (float)1000000;
					b = (float)5000000;
					
				}else if(key.equals("2")){
					a = (float)5000000;
					b = (float)8000000;
				}else if(key.equals("3")){
					a = (float)800000;
					b = (float)12000000;
				}else if(key.equals("4")){
					a = (float)12000000;
					b = (float)20000000;
				}
				logger.info("Client search with price from: "+ a + " to "+b);
				result = productS.SearchPrice(a, b);
				break;
			}
			case 2:{
				logger.info("Client search with color: "+ key);
				result = productS.SearchColor(key);
				break;
			}
			case 3:{
				logger.info("Client search with name: "+ key);
				result = productS.SearchName(key);
				break;
			}
		}
		
		return result;
	}
}
