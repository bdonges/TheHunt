package hunt.beans;

import java.util.Hashtable;

import com.mongodb.BasicDBObject;

public class Location extends BeanUtils 
{
	/**
	 * empty constructor
	 */
	public Location()
	{
	}
	
	/**
	 * populates a Location object from a BasicDBObject  
	 */
	public Location(BasicDBObject obj) 
	{
		convertDBObjectToLocation(obj);
	}	
	
	/**
	 * populates an Location object from data passed
	 * @param id
	 */
	public Location(String id, String huntId)
	{
		this.setId(id);
		this.setHuntId(huntId);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId"};
	private Hashtable<String,Object> data;
	private String json;
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Location object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertLocationToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Location
	 * @param obj
	 */
	public void convertDBObjectToLocation(BasicDBObject obj)
	{
		setJson(obj.toString());
		data = convertBasicDBObjectToObject(obj, cols);
	}		
	
	/**
	 * generates string for log entry
	 * @return
	 */
	public String getLogEntry()	
	{ 
		return generateLogEntry(data, "Location"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Location")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }

}
