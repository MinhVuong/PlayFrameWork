package helper;

import com.fasterxml.jackson.databind.JsonNode;

import models.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Http.Session;

public class SessionHelper {
	public User GetUser(String key){
		User user = new User();
		Session sessionM = Http.Context.current().session();
		String users = sessionM.get(key);
		
		if(users != null){
			JsonNode json = (JsonNode) Json.parse(users);
			user = (User) Json.fromJson(json, User.class);
		}
		return user;
	}
}
