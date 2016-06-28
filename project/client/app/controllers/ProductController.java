package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import models.AddCount;
import models.Address;
import models.CartEdit;
import models.CartProducts;
import models.CheckOut;
import models.InforCompare;
import models.InforConpareShow;
import models.Menu;
import models.PreCheckout;
import models.ProductCart;
import models.ProductEntity;
import models.ProductImageShow;
import models.ProductRelateShow;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;
import PakageResult.CartPakage;
import PakageResult.ComparePakage;
import PakageResult.OrderPakage;
import PakageResult.ProductDetailPakage;
import PakageResult.ProductPakage;
import PakageResult.User;
import business.CartService;
import business.SessionManager;
import views.html.*;
import com.fasterxml.jackson.databind.JsonNode;

import entities.ProductImageEntity;

public class ProductController extends Controller{
	SessionManager sessionM = new SessionManager();
	private CartService cartS = new CartService();
	private final Logger.ALogger log = Logger.of("product");
	public CompletionStage<Result> cart(int id)
	{
		Session session = Http.Context.current().session();
		CartProducts productsS = sessionM.GetCartProducts();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		
		String url = "http://localhost:9001/product/" + id;
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductPakage pakage = new ProductPakage();
    		pakage = Json.fromJson(jsonNode, ProductPakage.class);
    		
    		User user = new User(user1);
			Menu menu = new Menu(menu1);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            			CartProducts products = new CartProducts(productsS);
            			
            			ProductCart product = new ProductCart();
            			product = pakage.getProduct();
            			if(products==null)
            			{
            				products = new CartProducts();
            			}
            			
            			products.AddProduct(product);
            			session.put("products", Json.toJson(products).toString());
            			
            			
            			return ok(cart.render("", user, menu.getCategories(), menu.getCategoryProducts(), products));
            		}
            	case 0:{
            		return ok(cart.render("", user, menu.getCategories(), menu.getCategoryProducts(), productsS));
            		}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;
	}
	
	public Result getPriceByColor(String color, int id)
	{
		CartProducts products = sessionM.GetCartProducts();
		String price = products.getPriceByColor(color, id);
		CartEdit edit = new CartEdit();
		edit.setPrice(price);
		edit.setTotal(products.GetTotalProduct(id, color));
		edit.setTotalCart(products.GetPricesTotal(color));
		return ok(Json.toJson(edit));
	}
	
	public Result cartAdd(String color)
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.AddCountProduct(add.getId());
			
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			edit.setTotal(products.GetTotalProduct(add.getId(), color));
			edit.setTotalCart(products.GetPricesTotal(color));
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}
	public Result cartSub(String color)
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.SubCountProduct(add.getId());
			
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			edit.setTotal(products.GetTotalProduct(add.getId(), color));
			edit.setTotalCart(products.GetPricesTotal(color));
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}
	public Result cartDelete()
	{
		JsonNode json = request().body().asJson();
		AddCount add = new AddCount();
		add = Json.fromJson(json, AddCount.class);
		log.info("id: " + add.getId());
		if(add.getId() != 0)
		{
			sessionM.DeleteCountProduct(add.getId());
			CartProducts products = sessionM.GetCartProducts();
			CartEdit edit = new CartEdit();
			//edit.setTotal(products.GetTotalProduct(add.getId()));
			edit.setTotalCart(products.GetPricesTotal());
			edit.setCount(products.GetProductsTotal());
			return ok(Json.toJson(edit));
		}
		else
			return badRequest();
	}

	public CompletionStage<Result> checkout()
	{
		Session session = Http.Context.current().session();
		CartProducts productsS = sessionM.GetCartProducts();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		
		log.info("size product: " + productsS.getProducts().size());
		int id = 0;
		if(user1.getId() != null)
			id = user1.getId();
		PreCheckout entity = new PreCheckout();
		entity.setId(id);
		entity.setProducts(productsS);
		JsonNode json = Json.toJson(entity);
		
		String url = "http://localhost:9001/cart";
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		CartPakage pakage = new CartPakage();
    		pakage = Json.fromJson(jsonNode, CartPakage.class);
    		
    		User user = new User(user1);
			Menu menu = new Menu(menu1);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		int idc = pakage.getIdC();
            		log.info("idC: "+idc);
            		session.put("idc", Integer.toString(idc));
            		return ok(checkout.render(user, menu.getCategories(), menu.getCategoryProducts(), productsS));
            		}
            	case 0:{
            			return ok("Didn't connect to Server!");
            		}
            	case -1:{
        			
        			return ok(login.render("", "", menu.getCategories(), menu.getCategoryProducts()));
        			}
            	case -2:{
            		String message = "Cart empty!";
    				return ok(cart.render(message, user1, menu.getCategories(), menu.getCategoryProducts(), productsS));
        			}
            	case -3:{
            		String message = "Product ID " + pakage.getMessage() + " don't count to checkout!";
    				return ok(cart.render(message, user1, menu.getCategories(), menu.getCategoryProducts(), productsS));
        			}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;		
	}
	public Result cartInfor(String first, String last, String phone, String street, String city, int check)
	{
		Session session = Http.Context.current().session();
		Address address = new Address();
		address.setCity(city);
		address.setFirstName(first);
		address.setLastName(last);
		address.setPhone(phone);
		address.setStreet(street);
		log.info("" +check);
		if(check == 1){
			session.put("idA", Integer.toString(check));
		}else{
			session.put("idA", "0");
			session.put("addressc", Json.toJson(address).toString());
		}
		return ok(Json.toJson(address));
	}
	
	
	public CompletionStage<Result> checkoutSuccess()
	{
		Session session = Http.Context.current().session();
		CheckOut checkp = new CheckOut();
		User user1 = sessionM.GetUser();
		int id = 0;
		if(user1.getId() != null){
			id = user1.getId();
			checkp.setCustomerId(id);
		}
		int idc = sessionM.GetIdAddress();
		if(idc != 0){
			checkp.setAddressId(idc);
		}else{
			checkp.setAddressId(0);
			checkp.setAddress(sessionM.GetAddress());
		}
		checkp.setCartId(sessionM.GetIdCart());
		JsonNode json = Json.toJson(checkp);
		
		String url = "http://localhost:9001/cart/checkoutS";
		CompletionStage<WSResponse> receive  = WS.url(url).post(json);
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		OrderPakage pakage = new OrderPakage();
    		pakage = Json.fromJson(jsonNode, OrderPakage.class);
    		
    		//User user = new User(user1);
			//Menu menu = new Menu(menu1);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		session.remove("idc");
            		session.remove("idA");
            		session.remove("addressc");
            		session.remove("products");
            		return redirect(routes.HomeController.index());
            		}
            	case 0:{
            			return ok("Didn't connect to Server!");
            		}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;	
	}
	
	public CompletionStage<Result> productDetail(int id){
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		String url = "http://localhost:9001/product/detail/"+id;
		CompletionStage<WSResponse> receive  = WS.url(url).get();
    	CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ProductDetailPakage pakage = new ProductDetailPakage();
    		pakage = Json.fromJson(jsonNode, ProductDetailPakage.class);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		List<ProductImageShow> shows = new ArrayList<ProductImageShow>();
            		List<ProductImageEntity> images = pakage.getImages();
            		for(int i=0; i<images.size(); i++){
            			ProductImageShow show = new ProductImageShow();
            			show.ConvertFromProductImageEntity(images.get(i));
            			show.setTag(i+1);
            			shows.add(show);
            		}
            		List<ProductRelateShow> reShow = new ArrayList<ProductRelateShow>();
            		List<ProductEntity> relateP = pakage.getRelates();
            		int size = relateP.size();
            		for(int i=0; i<size; i= i+3){
            			ProductRelateShow sh = new ProductRelateShow();
            			sh.setProduct1(relateP.get(i));
            			if(i+1 < size)
            				sh.setProduct2(relateP.get(i+1));
            			if(i+2 < size)
            				sh.setProduct3(relateP.get(i+2));
            			sh.setTag(i+1);
            			reShow.add(sh);
            		}
            		log.info("size: " + reShow.size());
            		return ok(detail.render("", user1, menu1.getCategories(), menu1.getCategoryProducts(), pakage.getProduct(), pakage.getInfors(), shows, reShow));
            		}
            	case 0:{
            			return ok("Didn't connect to Server!");
            		}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;
	}
	public CompletionStage<Result> compare(int id){
		Session session = Http.Context.current().session();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		ProductEntity product1 = sessionM.getProductCompare1();
		InforCompare inforC1 = sessionM.getInforCompare1();
		String comS = session.get("productCompare1");
		if(comS != null){
			//inforC1 = sessionM.getInforCompare1();
			
		}
		
		
		String url = "http://localhost:9001/product/compare/"+id;
		CompletionStage<WSResponse> receive  = WS.url(url).get();
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		ComparePakage pakage = new ComparePakage();
    		pakage = Json.fromJson(jsonNode, ComparePakage.class);
    		
			//ComparePakage pakage1 = new ComparePakage(pakageT);
			
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType())
            	{
            	case 1:{
            		//return ok(compare1.render(user1, menu1.getCategories(), menu1.getCategoryProducts(), pakage.getProduct()));
         
            		if(null == comS){
            			session.put("productCompare1", Json.toJson(pakage.getProduct()).toString());
            			InforCompare inforC = new InforCompare();
            			inforC.setInfor(pakage.getInfor());
            			session.put("inforCompare1", Json.toJson(inforC).toString());
            			List<InforConpareShow> infors = inforC.convertToListFromInforList(pakage.getInfor());
            			
            			return ok(compare1.render(user1, menu1.getCategories(), menu1.getCategoryProducts(), pakage.getProduct(), infors));
            		}else{
            			//log.info("not null");
            			session.put("productCompare2", Json.toJson(pakage.getProduct()).toString());
            			InforCompare inforC = new InforCompare();
            			inforC.setInfor(pakage.getInfor());
            			session.put("inforCompare2", Json.toJson(inforC).toString());            			
            			
            			List<InforConpareShow> infors = inforC.convertToListFrom2InforList(inforC1.getInfor(), pakage.getInfor());
            			
            			return ok(compare.render(user1, menu1.getCategories(), menu1.getCategoryProducts(), product1, infors, pakage.getProduct()));
            		}
            		}
            	case 0:{
            			return ok("Didn't connect to Server!");
            		}
            	}
            	return ok("ok");            	
            }
             else
             {
            	 //logger.info("bad--------------------------");
            	 return badRequest("bad");
             }
         });
		
		return result;
		
	}
	
	public Result compareDel(int id){
		Session session = Http.Context.current().session();
		User user1 = sessionM.GetUser();
		Menu menu1 = sessionM.GetMenu();
		String nameDel = "productCompare"+id;
		session.remove(nameDel);
		session.remove("inforCompare"+id);
		ProductEntity product = new ProductEntity();
		List<InforConpareShow> infors = new ArrayList<InforConpareShow>();;
		InforCompare inforC = new InforCompare();
		String temp;
		if(id==1){
			temp = session.get("productCompare2");
			if(temp != null){
				product = sessionM.getProductCompare2();
				inforC = sessionM.getInforCompare2();
				infors = inforC.convertToListFromInforList(inforC.getInfor());
			
			}
		}else{
			temp = session.get("productCompare1");
			if(temp != null){
				product = sessionM.getProductCompare1();
				inforC = sessionM.getInforCompare1();
				infors = inforC.convertToListFromInforList(inforC.getInfor());
			}
		}
		if(temp == null)
			return redirect(routes.HomeController.index());
		else
			return ok(compare1.render(user1, menu1.getCategories(), menu1.getCategoryProducts(), product, infors));
	}
	public Result checkoutFail()
	{
		return ok("checkout fail");
		
	}
}
