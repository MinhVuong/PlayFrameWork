package controllers;

import helper.SessionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;

import models.OrderShow;
import models.OrderUpdate;
import models.StatusAndId;
import models.StatusList;
import models.User;
import pakageResult.OrderAdminPakage;
import play.data.Form;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import entities.CustomerEntity;
import entities.OrderEntity;
import views.html.*;
import views.html.helper.form;

public class OrderController extends Controller{
	private SessionHelper sessionH = new SessionHelper();
	
	public CompletionStage<Result> orders(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		String url = "http://localhost:9001/order/list/"+user.getToken();
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).get();
		CompletionStage<Result> result = receive.thenApply(resp -> {
            if(resp.getStatus()== 200)
            {	
            	JsonNode jsonNode = resp.asJson();
	    		OrderAdminPakage pakage = new OrderAdminPakage();
	    		pakage = Json.fromJson(jsonNode, OrderAdminPakage.class);
	    		List<OrderShow> shows = new ArrayList<OrderShow>();
        		List<CustomerEntity> customers = pakage.getCustomers();
        		for(OrderEntity o:pakage.getOrders()){
        			OrderShow or = new OrderShow();
        			or.ConvertFormOrderEntityAndCustomerEntity(o, GetNameFromListCustomer(customers, o.getCustomerId()));
        			shows.add(or);
        		}
        		StatusList statusL = new StatusList();
        		statusL.setStatus(AddStatusList());
        		return ok(order.render("ok", user, shows, statusL.getStatus(), role));
            	
            }
             else
             {
            	 return ok(login.render("", null));
             }
         });
		return result;
	}
	
	public String GetNameFromListCustomer(List<CustomerEntity> list, int id){
		for(CustomerEntity cu:list){
			if(cu.getId() == id)
				return cu.getFirstName();
		}
		return "";
	}
	public List<StatusAndId> AddStatusList(){
		List<StatusAndId> status = new ArrayList<StatusAndId>();
		StatusAndId st = new StatusAndId(1, "Created");
		status.add(st);
		st = new StatusAndId(2, "Shipping");
		status.add(st);
		st = new StatusAndId(3, "Complete");
		status.add(st);
		st = new StatusAndId(0, "Cancel");
		status.add(st);
		
		return status;
	}
	
	public CompletionStage<Result> orderUpdate(){
		User user = sessionH.GetUser("user");
		int role = sessionH.GetRole();
		OrderUpdate entity = Form.form(OrderUpdate.class).bindFromRequest().get();
		JsonNode json = Json.toJson(entity);
		
		String url = "http://localhost:9001/admin/order/update";
		CompletionStage<WSResponse> receive  = WS.url(url).setRequestTimeout(90000).post(json);
		CompletionStage<Result> result = receive.thenApply(resp -> {
            
    		JsonNode jsonNode = resp.asJson();
    		OrderAdminPakage pakage = new OrderAdminPakage();
    		pakage = Json.fromJson(jsonNode, OrderAdminPakage.class);
    		
            if(resp.getStatus()== 200)
            {	
            	switch(pakage.getType()){
            	case 1:{
            		List<OrderShow> shows = new ArrayList<OrderShow>();
            		List<CustomerEntity> customers = pakage.getCustomers();
            		for(OrderEntity o:pakage.getOrders()){
            			OrderShow or = new OrderShow();
            			or.ConvertFormOrderEntityAndCustomerEntity(o, GetNameFromListCustomer(customers, o.getCustomerId()));
            			shows.add(or);
            		}
            		StatusList statusL = new StatusList();
            		statusL.setStatus(AddStatusList());
            		return ok(order.render("ok", user, shows, statusL.getStatus(), role));
            	}
            	case 0:{
            		return ok("Server fail!");
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
}
