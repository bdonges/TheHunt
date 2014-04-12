package hunt.utils;


public class LoggerUtil 
{

	/**
	 * logs to sysout tacking a datetime on the front
	 * @param entry
	 */
	public static void logToOut(String entry)
	{
		try
		{
			System.out.println(DateUtil.getCurrentDateTime() + " :: " + entry);
		}
		catch (Exception e)
		{
			System.err.println("exception logging to out.  message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * logs to syserr tacking a datetime on the front
	 * @param entry
	 */
	public static void logToError(String entry)
	{
		try
		{
			System.err.println(DateUtil.getCurrentDateTime() + " :: " + entry);
		}
		catch (Exception e)
		{
			System.err.println("exception logging to err.  message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
