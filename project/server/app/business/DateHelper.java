package business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	// generate DateTime current Day.
	public String getDateTimeCurrent()
	{
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		result = dateFormat.format(date);
		return result;
	}
}
