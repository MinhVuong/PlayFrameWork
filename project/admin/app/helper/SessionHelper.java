package helper;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import models.User;
import play.libs.Json;
import play.libs.oauth.OAuth.RequestToken;
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
	
	public Optional<RequestToken> getSessionTokenPair() {
		Session sessionM = Http.Context.current().session();
		if (sessionM.containsKey("token")) {
			//return Optional.ofNullable(new RequestToken(session("token"),session("secret")));
			String token = sessionM.get("token");
			String secret = sessionM.get("secret");
			return Optional.ofNullable(new RequestToken(token, secret));
		}
		return Optional.empty();
	}
	
	public int GetRole(){
		Session sessionM = Http.Context.current().session();
		if (sessionM.containsKey("role")) {
			String role = sessionM.get("role");
			return Integer.parseInt(role);
		}
		return 0;
	}
}
