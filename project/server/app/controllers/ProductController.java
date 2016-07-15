package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import models.Category;
import models.CategoryProduct;
import models.IndexRequest;
import models.ProductCart;
import models.ProductGood;
import pakageResult.CategoryProduct2Pakage;
import pakageResult.CategoryProductPakage;
import pakageResult.ComparePakage;
import pakageResult.IndexFullPakage;
import pakageResult.IndexPagePakage;
import pakageResult.IndexPakage;
import pakageResult.ProductDetailPakage;
import pakageResult.ProductPakage;
import pakageResultAdmin.ProductAdminPakage;
import pakageResultAdmin.ProductCountAdminPakage;
import play.Logger;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.cache.*;
import services.CategoryProductService;
import services.OrderService;
import services.ProductCountService;
import services.ProductImageService;
import services.ProductInforService;
import services.ProductRealateService;
import services.ProductService;
import cache.ProductCache;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import entities.ProductCountEntity;
import entities.ProductEntity;
import entities.ProductRelateEntity;



public class ProductController extends Controller{
	private Logger.ALogger logger = Logger.of("product");
	private ProductService productS = new ProductService();
	private ProductCache productCache = new ProductCache();
	
	private ProductCountService productCountS = new ProductCountService();
	private CategoryProductService categoryProductS = new CategoryProductService();
	private ProductInforService inforS = new ProductInforService();
	private ProductImageService imageS = new ProductImageService();
	private ProductRealateService relateS = new ProductRealateService();
	private OrderService orderS = new OrderService();
	private final CacheApi cache;
	
	@Inject
	public ProductController(final CacheApi cache) {
		this.cache = cache;
	}

	public CompletionStage<Result> productList()
	{
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		IndexRequest indexR = new IndexRequest();
		CompletionStage<Result> result = null;
		
		indexR = Json.fromJson(json, IndexRequest.class);
		
		
		List<ProductEntity> smartphones = productCache.GetProductsByPage(indexR.getPageSmartphone(), indexR.getCountSmartphone(), 1, cache);
		List<ProductEntity> laptops =  productCache.GetProductsByPage(indexR.getPageLaptop(), indexR.getCountLaptop(), 2, cache);
		List<ProductEntity> tablets =  productCache.GetProductsByPage(indexR.getPageTablet(), indexR.getCountTablet(), 3, cache);
		
		IndexPakage indexP = new IndexPakage();
		IndexFullPakage pakage = new IndexFullPakage();
		
		if(smartphones.isEmpty() || laptops.isEmpty())
		{
			indexP.setType(0);
			pakage.setType(0);
			resp = status(500, Json.toJson(pakage));
		}
		else
		{
			indexP.setType(1);
			pakage.setType(1);
			if(!smartphones.isEmpty())
			{
				indexP.setSmartphones(smartphones);
				int pageS = productCache.PageTotal(1, indexR.getCountSmartphone(), cache);;
				indexP.setPageSmartphone(pageS);
			}
			if(!laptops.isEmpty())
			{
				indexP.setLaptop(laptops);
				int pageL = productCache.PageTotal(2, indexR.getCountLaptop(), cache);
				indexP.setPagelaptop(pageL);
			}
			if(!tablets.isEmpty())
			{
				indexP.setTablet(tablets);;
				int pageT = productCache.PageTotal(3, indexR.getCountTablet(), cache);
				indexP.setPagetablet(pageT);
			}
			
			
			pakage.setIndexPakage(indexP);
			List<CategoryEntity> categories = productCache.GetCategory(cache);
			List<Category> categoriesP = ChangeCategoryEntityToCategory(categories);
			pakage.setCategories(categoriesP);
			for(CategoryEntity category : categories){
				List<CategoryProductEntity> categoryProducts = productCache.GetCategoryProductByIdCategory(category.getId(), cache);
				pakage.addCategoryProducts(categoryProducts);
			}
			
			// Add product Good.
			logger.info("product good: ");
			List<ProductEntity> listG = new ArrayList<ProductEntity>();
			List<ProductGood> goods = orderS.GetIdProductGood();
			
			if(goods == null || goods.size() == 0){
				listG = productS.GetProductGood(3);
			}else{
				
				for(ProductGood or : goods){
					logger.info("good: " + Json.toJson(productS.GetProductById(or.getId())));
					listG.add(productS.GetProductById(or.getId()));
					
				}
			}
			
			pakage.setGoods(listG);
			resp = ok(Json.toJson(pakage));
		}
		
		
		return CompletableFuture.completedFuture(resp);
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
	
	public CompletionStage<Result> category(int id)
	{
		logger.info("Client view category: "+id);
		Result resp = new Result(200);
		CategoryProductPakage pakage = new CategoryProductPakage();
		
		List<CategoryProductEntity> categoryProducts = productS.GetCategoryProductByIdCategory(id);
		if(!categoryProducts.isEmpty())
		{
			List<CategoryProduct> categoryProductP = productS.ChangeCategoryProductEntityToCategoryProduct(categoryProducts);
			List<List<ProductEntity>> productss = new ArrayList<List<ProductEntity>>();
			for(CategoryProductEntity categoryProduct : categoryProducts){
				List<ProductEntity> products = productS.GetProductByCategory(categoryProduct.getId());
				productss.add(products);
			}
			
			pakage.setCategoryProducts(categoryProductP);
			pakage.setProductss(productss);
			pakage.setType(1);
			pakage.setName(productS.GetNameFromCategoryId(id));
			resp =  ok(Json.toJson(pakage));
			
		}
		else
		{
			pakage.setType(0);
			pakage.setName(productS.GetNameFromCategoryId(id));
			resp = status(500, Json.toJson(pakage));
		}
		
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> categoryProduct(int id, int idp)
	{
		logger.info("Client view Product category: "+id);
		Result resp = new Result(200);
		CategoryProduct2Pakage pakage = new CategoryProduct2Pakage();
		List<ProductEntity> products = productS.GetProductByCategory(idp);
		
		pakage.setType(1);
		pakage.setId(idp);
		pakage.setName(productS.GetNameFromProductId(idp));
		pakage.setProducts(products);
			
		
		resp =  ok(Json.toJson(pakage));
		return CompletableFuture.completedFuture(resp);
	}
	
	
	public Result productListAjax(int count, int page, int cate)
	{
		List<ProductEntity> products = productCache.GetProductsByPage(page, count, cate, cache);
		IndexPagePakage pakage = new IndexPagePakage();
		if(products.isEmpty())
		{
			pakage.setProducts(null);;
		}
		else
		{
			pakage.setProducts(products);
			
		}
		return ok(Json.toJson(pakage));
	}
	
	public CompletionStage<Result> product(int id)
	{
		logger.info("Client view Product: "+id);
		Result resp = new Result(200);
		ProductPakage pakage = new ProductPakage();
		if(id==0)
		{
			pakage.setType(0);
			pakage.setProduct(null);
			resp = ok(Json.toJson(pakage));
		}
		ProductEntity product = productS.GetProductById(id);
		
		ProductCart productC = new ProductCart();
		List<ProductCountEntity> counts = productCountS.GetListCountsProductById(id);
		
		productC.ConvertFromProductEntity(product, counts);
		if(product != null)
		{
			pakage.setType(1);
			pakage.setProduct(productC);
			resp = ok(Json.toJson(pakage));
		}else{
			pakage.setType(0);
			pakage.setProduct(productC);
			resp = ok(Json.toJson(pakage));
		}
		
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> productDetail(int id){
		logger.info("Client view Product Detail: "+id);
		ProductDetailPakage pakage = new ProductDetailPakage();
		pakage.setProduct(productS.GetProductById(id));
		pakage.setInfors(inforS.GetListInforProductByIdProduct(id));
		pakage.setImages(imageS.GetListImageProductBuIdProduct(id));
		List<ProductEntity> productR = new ArrayList<ProductEntity>();
		List<ProductRelateEntity> relates = relateS.GetProductRelateByProductId(id);
		for(ProductRelateEntity re : relates){
			ProductEntity p = productS.GetProductById(re.getProductRelate());
			productR.add(p);
		}
		pakage.setRelates(productR);
		pakage.setType(1);
		
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> compare(int id){
		logger.info("Client compare Product: "+id);
		ComparePakage pakage = new ComparePakage();
		pakage.setProduct(productS.GetProductById(id));
		pakage.setInfor(inforS.GetListInforProductByIdProduct(id));
		pakage.setImage(imageS.GetListImageProductBuIdProduct(id));
		pakage.setType(1);
		
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	
	
	/////////////////////////		ADMIN		/////////////
	
	public CompletionStage<Result> products(){
		
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(1);
		pakage.setProducts(productS.GetProducts());
		pakage.setCategories(categoryProductS.GetCategoryProductList());
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> productAdd(){
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		ProductEntity entity = Json.fromJson(json, ProductEntity.class);
		//String name = entity.getName().replace(';', ' ');
		//entity.setName(name);
		int result = productS.AddProduct(entity);
		
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setProducts(productS.GetProducts());
			pakage.setCategories(categoryProductS.GetCategoryProductList());
			resp = status(200, Json.toJson(pakage));	
		}else{
			resp = status(400, Json.toJson(pakage));			
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> productDel(int id){
		Result resp = new Result(200);
		int result = productS.DeleteProductById(id);
		
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setProducts(productS.GetProducts());
			pakage.setCategories(categoryProductS.GetCategoryProductList());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> productEdit(){
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		ProductEntity entity = Json.fromJson(json, ProductEntity.class);
		int result = productS.EditProduct(entity);
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setProducts(productS.GetProducts());
			pakage.setCategories(categoryProductS.GetCategoryProductList());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> cacs(){
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(1);
		pakage.setColors(productCountS.GetList());
		pakage.setCates(productS.GetCateProductToColorAndCount());
		return CompletableFuture.completedFuture(ok(Json.toJson(pakage)));
	}
	
	public CompletionStage<Result> cacAdd(){
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		ProductCountEntity entity = Json.fromJson(json, ProductCountEntity.class);
		int result = productCountS.AddProductCount(entity);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> cacEdit(){
		Result resp = new Result(200);
		JsonNode json = request().body().asJson();
		ProductCountEntity entity = Json.fromJson(json, ProductCountEntity.class);
		int result = productCountS.UpdateProductCount(entity);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	public CompletionStage<Result> cacDel(int id){		
		Result resp = new Result(200);
		int result = productCountS.DeleteProductCount(id);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
			resp = status(200, Json.toJson(pakage));
		}else{
			resp = status(400, Json.toJson(pakage));
		}
		return CompletableFuture.completedFuture(resp);
	}
	
	
	
	
	
	
	@AddCSRFToken
	public Result token()
	{
		Optional<CSRF.Token> token = CSRF.getToken(request());
		String result = token.get().value();
		response().setCookie("Csrf-Token", result);
		
		return ok(result);
	}
	
	@RequireCSRFCheck
	public Result demoCsrf()
	{
		
		return ok(CSRF.getToken(request()).map(t -> t.value()).orElse("no token"));
	}
	
	public Result removeProductCache(int page, int count, int cate){
		if(productCache.RemoveProductByPage(page, count, cate, cache))
			return ok("Success !!!");
		else
			return ok("Fail !!!");
		
	}
}
