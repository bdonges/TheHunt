package hunt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil 
{

	/**
	 * get's the current date time from system
	 * @return
	 * @throws Exception
	 */
	public static Date getCurrentDateTime() throws Exception
	{
		//return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(new java.util.Date().toString());
		return new java.util.Date();
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			Date d = DateUtil.getCurrentDateTime();
			System.out.println(d);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
