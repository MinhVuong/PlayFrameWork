package intercepter;

import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

public class Filters implements HttpFilters{

	@Inject
    CORSFilter corsFilter;
	
	@Override
	public EssentialFilter[] filters() {
		// TODO Auto-generated method stub
		return new EssentialFilter[] { corsFilter.asJava() };
	}

}
