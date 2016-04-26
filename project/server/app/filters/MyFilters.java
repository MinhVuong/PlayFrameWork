package filters;

import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.filters.csrf.CSRFFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

public class MyFilters implements HttpFilters {
	@Inject
    CORSFilter corsFilter;
	@Inject
	CSRFFilter csrfFilter;
	public EssentialFilter[] filters() {
		// TODO Auto-generated method stub
		return new EssentialFilter[] { csrfFilter.asJava(), corsFilter.asJava()};
	}
	

}
