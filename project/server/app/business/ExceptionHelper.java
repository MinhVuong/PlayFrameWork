package business;

import entities.ExceptionEntity;

public class ExceptionHelper {

	// Generate exceptionEntity when method had exception.
	public ExceptionEntity createExceptionEntityFromException(String method, String exception)
	{
		ExceptionEntity
		exceptionEntity = new ExceptionEntity();
		exceptionEntity.setMethod(method);
		exceptionEntity.setContent(exception);
		
		DateHelper dateHelper = new DateHelper();
		exceptionEntity.setTime(dateHelper.getDateTimeCurrent());
		return exceptionEntity;
	}
}
