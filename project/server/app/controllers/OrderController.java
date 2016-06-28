package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.OrderUpdate;
import business.MailHelper;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CartEntity;
import entities.CustomerEntity;
import entities.OrderEntity;
import pakageResultAdmin.OrderAdminPakage;
import play.mvc.Controller;
import play.mvc.Result;
import services.CartService;
import services.CustomerService;
import services.OrderService;
import play.libs.Json;
import play.libs.mailer.MailerClient;

public class OrderController extends Controller{
	private OrderService orderS = new OrderService();
	private CustomerService customerS = new CustomerService();
	private CartService cartS = new CartService();
	private final MailerClient mailerClient;
	@Inject
	public OrderController(final MailerClient client){
		this.mailerClient = client;
	}
	
	public Result orderUpdate(){
		JsonNode json = request().body().asJson();
		OrderUpdate order = Json.fromJson(json, OrderUpdate.class);
		
		OrderAdminPakage pakage = new OrderAdminPakage();
		if(order.getId() == -1){
			pakage.setType(1);
		}else{
			OrderEntity entity = orderS.GetOrderById(order.getId());
			CartEntity cartE = cartS.GetEntityById(entity.getCartId());
			List<String> names = CutContent(cartE.getProductName());
			List<String> counts = CutContent(cartE.getProductCount());
			List<String> prices = CutContent(cartE.getProductPrice());
			
			CustomerEntity customer = customerS.GetCustomerFromID(cartE.getCustomerId());
			MailHelper mailer = new MailHelper(this.mailerClient);
			boolean giveM = mailer.ChangeStatusOrder(customer.getFirstName(), customer.getEmail(), names, counts, prices, order.getStatus());
			
			int result = orderS.UpdateEntity(order.getId(), order.getStatus());
			if(giveM == true && result == 1)
				pakage.setType(1);
			else
				pakage.setType(0);
		}
		pakage.setOrders(orderS.GetList());
		pakage.setCustomers(customerS.GetListCustomer());
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
}
