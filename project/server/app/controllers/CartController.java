package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.CheckOut;
import models.PreCheckout;
import models.ProductGood;
import pakageResult.CartPakage;
import pakageResult.OrderPakage;
import pakageResult.ProductGoodPakage;
import pakageResultAdmin.OrderAdminPakage;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import services.AddressService;
import services.CartService;
import services.CustomerService;
import services.OrderService;
import services.ProductCountService;
import services.ProductService;
import business.MailHelper;

import com.fasterxml.jackson.databind.JsonNode;

import entities.AddressEntity;
import entities.CartEntity;
import entities.CustomerEntity;
import entities.OrderEntity;
import entities.ProductEntity;

public class CartController extends Controller{
	private CartService cartS = new CartService();
	private ProductCountService productCountS = new ProductCountService();
	private AddressService addressS = new AddressService();
	private OrderService orderS = new OrderService();
	private ProductService productS = new ProductService();
	private CustomerService customerS = new CustomerService();
	
	private final MailerClient mailerClient;
	@Inject
	public CartController(final MailerClient client){
		this.mailerClient = client;
	}
	public Result cart()
	{
		JsonNode json = request().body().asJson();
		PreCheckout cart = new PreCheckout();
		cart = Json.fromJson(json,PreCheckout.class );
		CartPakage pakage = new CartPakage();
		if(cart.getId()==0)
		{
			pakage.setType(-1);
		}else
		{
			if(cart.getProducts().getProducts().size() == 0)
			{
				pakage.setType(-2);
			}else{
				int check = productCountS.CheckCountProductCart(cart.getProducts());
				if(check == 0){
					CartEntity entity = new CartEntity();
					entity = cartS.CreateCartEntity(cart.getProducts(), cart.getId());
					int idc = cartS.AddCartReturnId(entity);
					if(idc != 0)
					{
						pakage.setType(1);
						pakage.setIdC(idc);
					}else{
						pakage.setType(0);
					}
				}else{
					pakage.setType(-3);
					pakage.setMessage(Integer.toString(check));
				}
				
			}
		}
		
		return ok(Json.toJson(pakage));
	}
	
	public Result checkoutS(){
		JsonNode json = request().body().asJson();
		CheckOut checkp = new CheckOut();
		checkp = Json.fromJson(json, CheckOut.class);
		
		CartEntity cartE = cartS.GetEntityById(checkp.getCartId());
		OrderEntity entity = new OrderEntity();
		entity.ConvertFromCheckOut(checkp, cartE);
		
		
		if(checkp.getAddressId() == 1){
			AddressEntity addressE = addressS.GetAddress(checkp.getCustomerId());
			entity.setAddressId(addressE.getId());
		}else{
			
		}
		List<String> names = CutContent(cartE.getProductName());
		List<String> counts = CutContent(cartE.getProductCount());
		List<String> prices = CutContent(cartE.getProductPrice());
		MailHelper mailer = new MailHelper(this.mailerClient);
		CustomerEntity customer = customerS.GetCustomerFromID(checkp.getCustomerId());
		boolean giveM = mailer.GiveMailWhenCheckoutSuccess(customer.getFirstName(), customer.getEmail(), names, counts, prices);
		
		OrderPakage pakage = new OrderPakage();
		int result = orderS.AddOrder(entity);
		if(giveM == true && result == 1)
			pakage.setType(1);
		else
			pakage.setType(0);
		return ok(Json.toJson(pakage));
	}
	private List<String> CutContent(String content){
		List<String> result = new ArrayList<String>();
		String[] arr = content.split(";");
		for(int i = 0; i<arr.length; i++){
			if(!("".equals(arr[i]))){
				result.add(arr[i]);
			}
		}
		return result;
	}
	
	public Result productGood(){
		ProductGoodPakage pakage = new ProductGoodPakage();
		
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		for(ProductGood or : orderS.GetIdProductGood()){
			list.add(productS.GetProductById(or.getId()));
		}
		pakage.setProducts(list);
		return ok(Json.toJson(pakage));
	}
	
	
	public Result orders(){
		OrderAdminPakage pakage = new OrderAdminPakage();
		pakage.setType(1);
		pakage.setOrders(orderS.GetList());
		pakage.setCustomers(customerS.GetListCustomer());
		return ok(Json.toJson(pakage));
	}
	
	public Result count()
	{
		int count = productCountS.GetCountProductById(1, "B");
		return ok(Integer.toString(count));
	}
}
