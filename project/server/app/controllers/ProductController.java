package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import models.Category;
import models.CategoryProduct;
import models.IndexRequest;
import models.ProductCart;
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
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CategoryProductService;
import services.ProductCountService;
import services.ProductImageService;
import services.ProductInforService;
import services.ProductRealateService;
import services.ProductService;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CategoryEntity;
import entities.CategoryProductEntity;
import entities.ProductCountEntity;
import entities.ProductEntity;
import entities.ProductRelateEntity;



public class ProductController extends Controller{

	private ProductService productS = new ProductService();
	private ProductCountService productCountS = new ProductCountService();
	private CategoryProductService categoryProductS = new CategoryProductService();
	private ProductInforService inforS = new ProductInforService();
	private ProductImageService imageS = new ProductImageService();
	private ProductRealateService relateS = new ProductRealateService();
	public Result productList()
	{
		JsonNode json = request().body().asJson();
		IndexRequest indexR = new IndexRequest();
		CompletionStage<Result> result = null;
		
		indexR = Json.fromJson(json, IndexRequest.class);
		
		
		List<ProductEntity> smartphones =  productS.GetProductsByPage(indexR.getPageSmartphone(), indexR.getCountSmartphone(), 1);
		List<ProductEntity> laptops =  productS.GetProductsByPage(indexR.getPageLaptop(), indexR.getCountLaptop(), 2);
		IndexPakage indexP = new IndexPakage();
		IndexFullPakage pakage = new IndexFullPakage();
		
		if(smartphones.isEmpty() || laptops.isEmpty())
		{
			indexP.setType(0);
			pakage.setType(0);
		}
		else
		{
			indexP.setType(1);
			pakage.setType(1);
			if(!smartphones.isEmpty())
			{
				indexP.setSmartphones(smartphones);
				int pageS = productS.PageTotal(1, indexR.getCountSmartphone());
				indexP.setPageSmartphone(pageS);
			}
			if(!laptops.isEmpty())
			{
				indexP.setLaptop(laptops);
				int pageL = productS.PageTotal(2, indexR.getCountLaptop());
				indexP.setPagelaptop(pageL);
			}
			pakage.setIndexPakage(indexP);
			List<CategoryEntity> categories = productS.GetCategory();
			List<Category> categoriesP = productS.ChangeCategoryEntityToCategory(categories);
			pakage.setCategories(categoriesP);
			for(CategoryEntity category : categories){
				List<CategoryProductEntity> categoryProducts = productS.GetCategoryProductByIdCategory(category.getId());
				pakage.addCategoryProducts(categoryProducts);
			}
		}
		
		return ok(Json.toJson(pakage));
	}
	
	public Result category(int id)
	{
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
			
		}
		else
		{
			pakage.setType(0);
			pakage.setName(productS.GetNameFromCategoryId(id));
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result categoryProduct(int id, int idp)
	{
		CategoryProduct2Pakage pakage = new CategoryProduct2Pakage();
		List<ProductEntity> products = productS.GetProductByCategory(idp);
		
		pakage.setType(1);
		pakage.setId(idp);
		pakage.setName(productS.GetNameFromProductId(idp));
		pakage.setProducts(products);
			
		
		return ok(Json.toJson(pakage));
	}
	
	
	public Result productListAjax(int count, int page, int cate)
	{
		List<ProductEntity> products =  productS.GetProductsByPage(page, count, cate);
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
	
	public Result product(int id)
	{
		ProductPakage pakage = new ProductPakage();
		if(id==0)
		{
			pakage.setType(0);
			pakage.setProduct(null);
			return ok(Json.toJson(pakage));
		}
		ProductEntity product = productS.GetProductById(id);
		
		ProductCart productC = new ProductCart();
		List<ProductCountEntity> counts = productCountS.GetListCountsProductById(id);
		
		productC.ConvertFromProductEntity(product, counts);
		if(product != null)
		{
			pakage.setType(1);
			pakage.setProduct(productC);
		}else{
			pakage.setType(0);
			pakage.setProduct(productC);
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result productDetail(int id){
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
		return ok(Json.toJson(pakage));
	}
	
	public Result compare(int id){
		ComparePakage pakage = new ComparePakage();
		pakage.setProduct(productS.GetProductById(id));
		pakage.setInfor(inforS.GetListInforProductByIdProduct(id));
		pakage.setImage(imageS.GetListImageProductBuIdProduct(id));
		pakage.setType(1);
		return ok(Json.toJson(pakage));
	}
	
	
	
	/////////////////////////		ADMIN		/////////////
	
	public Result products(){
		
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(1);
		pakage.setProducts(productS.GetProducts());
		pakage.setCategories(categoryProductS.GetCategoryProductList());
		return ok(Json.toJson(pakage));
	}
	
	public Result productAdd(){
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
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result productDel(int id){
		
		int result = productS.DeleteProductById(id);
		
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setProducts(productS.GetProducts());
			pakage.setCategories(categoryProductS.GetCategoryProductList());
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result productEdit(){
		JsonNode json = request().body().asJson();
		ProductEntity entity = Json.fromJson(json, ProductEntity.class);
		int result = productS.EditProduct(entity);
		ProductAdminPakage pakage = new ProductAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setProducts(productS.GetProducts());
			pakage.setCategories(categoryProductS.GetCategoryProductList());
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result cacs(){
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(1);
		pakage.setColors(productCountS.GetList());
		pakage.setCates(productS.GetCateProductToColorAndCount());
		return ok(Json.toJson(pakage));
	}
	
	public Result cacAdd(){
		JsonNode json = request().body().asJson();
		ProductCountEntity entity = Json.fromJson(json, ProductCountEntity.class);
		int result = productCountS.AddProductCount(entity);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result cacEdit(){
		JsonNode json = request().body().asJson();
		ProductCountEntity entity = Json.fromJson(json, ProductCountEntity.class);
		int result = productCountS.UpdateProductCount(entity);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
		}
		return ok(Json.toJson(pakage));
	}
	
	public Result cacDel(int id){		
		int result = productCountS.DeleteProductCount(id);
		
		ProductCountAdminPakage pakage = new ProductCountAdminPakage();
		pakage.setType(result);
		if(result != 0){
			pakage.setColors(productCountS.GetList());
			pakage.setCates(productS.GetCateProductToColorAndCount());
		}
		return ok(Json.toJson(pakage));
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
	
}
