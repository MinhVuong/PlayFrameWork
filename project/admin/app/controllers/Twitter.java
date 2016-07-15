package controllers;

import pakageResult.LoginAdminPakage;
import play.Logger;
import play.libs.Json;
import play.libs.oauth.OAuth;
import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.OAuthCalculator;
import play.libs.oauth.OAuth.RequestToken;
import play.libs.oauth.OAuth.ServiceInfo;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;

import entities.AdminRoleEntity;

import javax.inject.Inject;

import models.Oauth1;
import models.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Twitter extends Controller{
	
	static final ConsumerKey KEY = new ConsumerKey("9rP8YMBPgleTBTwBVL1Q4RzpS",
			"xB69Lkxr7UVU4PqzMwD3wRi35qd0NH8YgqVipb2K4iNwl1s4ZO");

	private static final ServiceInfo SERVICE_INFO = new ServiceInfo(
			"https://api.twitter.com/oauth/request_token",
			"https://api.twitter.com/oauth/access_token",
			"https://api.twitter.com/oauth/authorize", KEY);

	private static final OAuth TWITTER = new OAuth(SERVICE_INFO);

	private final WSClient ws;

	@Inject
	public Twitter(WSClient ws) {
		this.ws = ws;
	}
	
	public CompletionStage<Result> authentication() {
		String token = "740731931336155137-ZX8IZWvaSiASGXFNhXeLgrBlBJX05Z9";
		String secret = "dVrwuRrR2fJO8iRY8XfwJIJPG0JCeu3KMXT3CeUz8ipUL";
		RequestToken requestToken = getSessionTokenPair(token, secret).get();
		Oauth1 pakage = new Oauth1();
		pakage.setToken(token);
		pakage.setSecret(secret);
		JsonNode json = Json.toJson(pakage);
		String url = "http://localhost:9001/twitter/homeTimeline/"+token+"/"+secret;
		CompletionStage<WSResponse> receive  = ws.url(url).get();
		CompletionStage<Result> result = receive.thenApply(resp -> {
            if(resp.getStatus()== 200)
            {	
            	return ok("ok");
            	
            }else{
            	return badRequest("false: " + resp.getStatus());
            }
         });
		return result;
    }		
	
	 public CompletionStage<Result> homeTimeline(String a, String b) {
	        Optional<RequestToken> sessionTokenPair = getSessionTokenPair(a, b);
	        if (sessionTokenPair.isPresent()) {
	        	CompletionStage<WSResponse> receive = ws
	        			.url("https://api.twitter.com/1.1/statuses/home_timeline.json")
	                    .sign(new OAuthCalculator(Twitter.KEY, sessionTokenPair.get()))
	                    .get();
	        	CompletionStage<Result> result = receive.thenApply(resp -> {
	                if(resp.getStatus()== 200)
	                {	
	                	return ok("200");
	                }else {
	                	return ok("Bad status code: " + resp.getStatus());
	                }
	             });
	            return result;
	        }
	        return CompletableFuture.completedFuture(redirect(routes.Twitter.auth()));
	    }
	 
	public Result auth() {

		String verifier = request().getQueryString("oauth_verifier");
		if (Strings.isNullOrEmpty(verifier)) {
			String url = routes.Twitter.auth().absoluteURL(request());
			RequestToken requestToken = TWITTER.retrieveRequestToken(url);
			return redirect(TWITTER.redirectUrl(requestToken.token));
			// return status(401, "Strange response type");
		} else {
			String token = session("token");
			String secret = session("secret");
			RequestToken requestToken = getSessionTokenPair(token, secret).get();
			RequestToken accessToken = TWITTER.retrieveAccessToken(requestToken, verifier);
			saveSessionTokenPair(accessToken);
			
			//return redirect(routes.Twitter.homeTimeline());
			//return redirect(routes.Twitter.homeTimeline(requestToken.token, requestToken.secret));
			return ok("token: " + accessToken.token + "\nsecret: " + accessToken.secret);
		}
	}

	private void saveSessionTokenPair(RequestToken requestToken) {
		session("token", requestToken.token);
		session("secret", requestToken.secret);
	}

	private Optional<RequestToken> getSessionTokenPair(String a, String b) {
		if (session().containsKey("token")) {
			//return Optional.ofNullable(new RequestToken(session("token"),session("secret")));
			return Optional.ofNullable(new RequestToken(a, b));
		}
		return Optional.empty();
	}
}
