package controllers;

import models.PreCheckout;

import com.fasterxml.jackson.databind.JsonNode;

import entities.CartEntity;
import pakageResult.CartPakage;
import play.libs.Json;
import play.mvc.Controller;
import services.CartService;
import services.ProductCountService;
import play.mvc.Result;

public class CartController extends Controller{
	private CartService cartS = new CartService();
	private ProductCountService productCountS = new ProductCountService();
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
					if(cartS.AddCart(entity))
					{
						pakage.setType(1);
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
	
	public Result count()
	{
		int count = productCountS.GetCountProductById(1);
		return ok(Integer.toString(count));
	}
}
