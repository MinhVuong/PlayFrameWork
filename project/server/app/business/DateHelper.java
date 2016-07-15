package business;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;






import org.joda.time.DateTime;
import org.joda.time.Hours;

import play.Logger;
import entities.ExceptionEntity;
import services.ExceptionService;

public class DateHelper {

	private ExceptionService exceptionService = new ExceptionService();
	private ExceptionHelper exceptionHelper = new ExceptionHelper();
	
	// Result DateTime current Day.
	public String getDateTimeCurrent()
	{
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		result = dateFormat.format(date);
		return result;
	}
	// Get Time Expiry to verify account by email. Time = Time Registed + 24 hours
	public String GetDateTimeExpiryEmail(String time)
	{
		String result="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date;
		try {
			date = dateFormat.parse(time);
			
			DateTime dt1 = new DateTime(date);
			dt1 = dt1.plusDays(1);
			date = dt1.toDate();
			return dateFormat.format(date);
			
		} catch (ParseException e) {
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("GetDateTimeExpiryEmail(String time)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
		}
		return result;
	}
	
	// Calculation between two time: current time and start.
	public int CalculationBetweenTwoTime(String time)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date_Current = new Date();
		try {
			Date date = (Date)dateFormat.parse(time);
			
			DateTime dt1 = new DateTime(date);
			DateTime dt2 = new DateTime(date_Current);
			
			int count = Hours.hoursBetween(dt1, dt2).getHours();
			return count;
			
		} catch (ParseException e) {
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CalculationBetweenTwoTime(String time)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
		}
		
		return 0;
	}
	// Calculation between two time: stop and start.
	public int CalculationBetweenTwoTime(String start, String stop)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try {
			Date date1 = (Date)dateFormat.parse(start);
			Date date2 = (Date)dateFormat.parse(stop);
			
			DateTime dt1 = new DateTime(date1);
			DateTime dt2 = new DateTime(date2);
			
			int count = Hours.hoursBetween(dt2, dt1).getHours();
			return count;
			
		} catch (ParseException e) {
			ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CalculationBetweenTwoTime(String time)", e.getMessage());
			exceptionService.AddException(exceptionEntity);
		}
		
		return 0;
	}
	// Time Token: stop and start.
		public int TimeExpireToken(String start)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stop = getDateTimeCurrent();
			try {
				Date date1 = (Date)dateFormat.parse(start);
				Date date2 = (Date)dateFormat.parse(stop);
				
				long t1 = date1.getTime();
				Logger.info("t1: " + t1);
				long t2 = date2.getTime();
				Logger.info("t2: " + t2);
				long t = t2 - t1;
				
				t = t/3600;
				Logger.info("t/3600: " + t);
				if(t<1800)
					return 1;
				else
				return 0;
				
			} catch (ParseException e) {
				ExceptionEntity exceptionEntity = exceptionHelper.createExceptionEntityFromException("CalculationBetweenTwoTime(String time)", e.getMessage());
				exceptionService.AddException(exceptionEntity);
				return 0;
			}
			
			
		}
}
