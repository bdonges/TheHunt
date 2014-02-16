package hunt.test;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class TestUtils 
{

	public static void show(String colName, DBCollection c)
	{
		DBCursor cursor = c.find();
		System.out.println("  show: " + colName);
		try 
		{	
		   while(cursor.hasNext()) 
		   {
		       System.out.println("    " + cursor.next());
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		
	}
	
}
