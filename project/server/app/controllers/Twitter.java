package controllers;

import pakageResultAdmin.Oauth1Pakage;
import play.Logger;
import play.libs.Json;
import play.libs.oauth.OAuth;
import play.libs.oauth.OAuth.ConsumerKey;
import play.libs.oauth.OAuth.OAuthCalculator;
import play.libs.oauth.OAuth.RequestToken;
import play.libs.oauth.OAuth.ServiceInfo;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;

import javax.inject.Inject;

import models.Oauth1;
import models.Register;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Twitter extends Controller {
	private Logger.ALogger loger = Logger.of("oauth");
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

	public CompletionStage<Result> homeTimeline(String a, String b) {
		Logger.info("token: " + a);
		Logger.info("secret: " + b);
    	Optional<RequestToken> sessionTokenPair = getSessionTokenPair(a, b);
        if (sessionTokenPair.isPresent()) {
        	CompletionStage<WSResponse> receive = ws.url("https://api.twitter.com/1.1/statuses/home_timeline.json")
                    .sign(new OAuthCalculator(Twitter.KEY, sessionTokenPair.get()))
                    .get();
        	
        	CompletionStage<Result> result = receive.thenApply(resp -> {
        		
        		Oauth1Pakage pakage = new Oauth1Pakage();
        		
                if(resp.getStatus()== 200)
                {	
                	pakage.setType(1);
                	return ok("200");
                }else {
                	pakage.setType(resp.getStatus());
                	return status(resp.getStatus());
                }
             });
            return result;
        }
        return CompletableFuture.completedFuture(status(401));
    }

	public Result auth() {

		String verifier = request().getQueryString("oauth_verifier");
		if (Strings.isNullOrEmpty(verifier)) {
			String url = routes.Twitter.auth().absoluteURL(request());
			RequestToken requestToken = TWITTER.retrieveRequestToken(url);
			saveSessionTokenPair(requestToken);
			return redirect(TWITTER.redirectUrl(requestToken.token));
		} else {
			String token = session("token");
			String secret = session("secret");
			RequestToken requestToken = getSessionTokenPair(token, secret).get();
			RequestToken accessToken = TWITTER.retrieveAccessToken(requestToken, verifier);
			saveSessionTokenPair(accessToken);
			Logger.info("token: " + requestToken.token);
			Logger.info("secret: " + requestToken.secret);
			return ok("token: " + accessToken.token + "\nsecret: " + accessToken.secret);
		}
	}

	private void saveSessionTokenPair(RequestToken requestToken) {

		session("token", requestToken.token);
		session("secret", requestToken.secret);
	}

	private Optional<RequestToken> getSessionTokenPair(String a, String b) {
		return Optional.ofNullable(new RequestToken(a, b));
	}

}
