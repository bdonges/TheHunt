package hunt.db;

import hunt.business.HuntCommand;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoManager 
{

	public static void main(String[] args)
	{
		try
		{
			MongoManager mm = new MongoManager();
			
			MongoFactory f = new MongoFactory();
			String ip = "127.0.0.1";
			int port = 27017;
			
			String test = "huntupserttest";
			if (test.equals("test"))
				mm.test(f, ip, port);
			//else if (test.equals("huntupserttest"))
			//	mm.huntUpsertTest();
			else if (test.equals("upserttest"))
				mm.testUpsert(f, ip, port);
		}
		catch (Exception e)
		{
			System.err.println("exception - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void test(MongoFactory f, String ip, int port) throws Exception
	{
		// get mongo
		Mongo m = f.getConnection(ip,port);
		System.out.println("is mongo running at " + ip + ":" + port + "?  "+f.mongoRunningAt(m));
		
		// get db
		DB db = f.getDatabase(m, "hunt_test_1");

		// get collections in db
		Set <String> names = db.getCollectionNames();
		for (String name : names)
			System.out.println("name: " + name);

		
		// close
		f.close(db);
		m.close();
		
	}
	
	
	public void testUpsert(MongoFactory f, String ip, int port) 
	{
        try 
        {
        	System.out.println("Mongodb Upsert object example...");
            // Creating mongoinstance
        	Mongo mongo = new Mongo(ip, port);

        	// Creating database instance
            DB db = mongo.getDB("hunt_test_1");
            
            // Creating collection object
            DBCollection collection = db.getCollection("upsert_test");
            final BasicDBObject query = new BasicDBObject("name", "pretech");
            
            // Creating BasicDBObjectBuilder object without arguments
            DBObject documentBuilder = BasicDBObjectBuilder.start()
            		.add("address", "bangalore")
            		.add("phone", "9988998899")
            		.get();
            
            // get the dbobject from builder and Inserting document
            collection.update(query, new BasicDBObject("$set", documentBuilder), true, false);
            
            // fetch the inserted value
            System.out.println("Inserted record"+ collection.findOne(new BasicDBObject("name", "pretech")));
            
            // Retrieving collection details
            DBCursor cursorDoc = collection.find();
            while(cursorDoc.hasNext()) 
            	System.out.println("After insert pretech details " + cursorDoc.next());
            
            DBObject documentBuilder1 = BasicDBObjectBuilder.start()
            		.add("address", "chennai")
            		.add("phone", "9999999999")
            		.get();
            
            // get the dbobject from builder and upserting document
            collection.update(query, new BasicDBObject("$set", documentBuilder1), true, false);
            
            // Retrieving collection details
            DBCursor cursorDoc1 = collection.find();
            while(cursorDoc1.hasNext()) 
            	System.out.println("After upsert pretech details " + cursorDoc1.next());
            
            // Retrieving collection details
            DBCursor newcursorDoc = collection.find();
            System.out.println("No of records exists" + newcursorDoc.count());
            System.out.println("End of program");
        } 
        catch(UnknownHostException e) 
        {
               e.printStackTrace();
        } 
        catch (MongoException e) 
        {
               e.printStackTrace();
        }
	}	
	
}
