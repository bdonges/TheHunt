package hunt.db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Hunt;
import hunt.beans.Location;

public class LocationManager 
{

	private final String COLLECTION_NAME = "locations";
	
	/**
	 * 
	 */
	public LocationManager() 
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
	public Location upsert(Location location, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", location.getId()), 
			    location.convertLocationToBasicDBObject(), true, false);
		return findOne(location.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Location findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Location location = new Location();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				location.convertDBObjectToLocation(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return location;
	}
	
	public void deleteLocation(String locationId, DB db)
	{
		Location location = findOne(locationId, db);
		
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Location> getAllForHunt(Hunt hunt, DB db)
	{
		ArrayList<Location> locations = new ArrayList<Location>();
		
		BasicDBObject query = new BasicDBObject("huntId", hunt.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   locations.add(new Location(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return locations;
	}
	
}
