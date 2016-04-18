package intercepter;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Request;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.mvc.*;
import views.html.*;
import static play.mvc.Results.*;

public class Global extends GlobalSettings {
	private static final Logger.ALogger logger = Logger.of("intercepter");
	  
	  @Override
	  public Action onRequest(Request request, Method method) {
	   //logger.info(request.path());
	   //logger.info(request.method());
	    
	    return super.onRequest(request, method);
	  }
	  
	  @Override
	  public void onStart(Application app) {
		  logger.info("Application has started");
	  }

	  @Override
	  public void onStop(Application app) {
		  logger.info("Application has stopped");
	  }
	  
	  
	  public CompletionStage<Result> onError(RequestHeader request, Throwable t) {
		  logger.info("Error");
		  return CompletableFuture.completedFuture(
	                Results.ok(errorp.render(t.getMessage()))
	        );
	    }
	  /*
	  public static F.Promise<Result> wrapResultAsPromise(final Result result) {
		    return F.Promise.promise(
		        new F.Function0<Result>() {
		            public Result apply() {
		                return result;
		            }
		        }
		    );
		}
	  
	  @Override
	  public CompletionStage<Result> onHandlerNotFound(RequestHeader request) {
		  logger.info("action not found");
		  logger.info("url: " + request.host() + request.path());
		  logger.info("method: " + request.method());
		  return Promise.<Result> pure(Results.internalServerError(t.getMessage()));
	  }  */
}
