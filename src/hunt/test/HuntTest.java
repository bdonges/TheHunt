package hunt.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.db.MongoFactory;

public class HuntTest extends TestUtils
{

	private static final String COLLECTION_NAME = "hunts";
	
	public static void main(String[] args)
	{
		try
		{	
			System.out.println("starting...");
			System.out.println("getting mongo factory");
			MongoFactory mf = new MongoFactory();
			System.out.println("getting hunt db");
			DB db = mf.getDatabase("192.168.0.105", "hunt", 27017);
			
			// get all rows from account collection
			System.out.println("getting all");
			HuntTest.getAll(db);
			System.out.println("inserting");
			HuntTest.insert(db);
			System.out.println("getting all");
			HuntTest.getAll(db);
			
			System.out.println("done");
		}
		catch (Exception e)
		{
			System.err.println("error - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void getOne(String _id)
	{
		
	}
	
	public static void getAll(DB db)
	{
		DBCollection accounts = db.getCollection(COLLECTION_NAME);
		show(COLLECTION_NAME, accounts);
	}
	
	public static void insert(DB db)
	{
		DBCollection accounts = db.getCollection(COLLECTION_NAME);
		BasicDBObject doc = new BasicDBObject("first", "Bill").
                append("last", "Donges").
                append("email", "bdongesus@yahoo.com").
                append("phone", "678.793.0698");
                //append("info", new BasicDBObject("x", 203).append("y", 102));

		accounts.insert(doc);
	}
	
}
