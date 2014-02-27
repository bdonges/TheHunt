package hunt.db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Hunt;

public class HuntManager 
{

	private final String COLLECTION_NAME = "hunts";
	
	/**
	 * 
	 */
	public HuntManager() 
	{
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public DBCollection getCollection(DB db)
	{
		return db.getCollection(COLLECTION_NAME);
	}
	
	/**
	 * 
	 * @param account
	 * @param db
	 * @return
	 */
	public Hunt upsert(Hunt hunt, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", hunt.getId()), 
			    hunt.convertHuntToBasicDBObject(), true, false);
		return findOne(hunt.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Hunt findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Hunt hunt = new Hunt();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				hunt.convertDBObjectToHunt(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return hunt;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Hunt> getAllHunts(DB db)
	{
		ArrayList<Hunt> hunts = new ArrayList<Hunt>();

		DBCollection c = getCollection(db);
		DBCursor cursor = c.find();
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   hunts.add(new Hunt(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return hunts;
	}
		
}
