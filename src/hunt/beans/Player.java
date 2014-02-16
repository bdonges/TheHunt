package hunt.beans;

import java.util.Hashtable;

import com.mongodb.BasicDBObject;

public class Player extends BeanUtils
{

	/**
	 * empty constructor
	 */
	public Player()
	{
	}
	
	/**
	 * populates a Player object from a BasicDBObject  
	 */
	public Player(BasicDBObject obj) 
	{
		convertDBObjectToPlayer(obj);
	}	
	
	/**
	 * populates an Player object from data passed
	 * @param id
	 */
	public Player(String id, String huntId, String teamId, String firstName, String lastName, String email)
	{
		this.setId(id);
		this.setHuntId(huntId);
		this.setTeamId(teamId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId","teamId","firstName","lastName","email"};
	private Hashtable<String,Object> data;
	private String json;
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Player object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertPlayerToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Player
	 * @param obj
	 */
	public void convertDBObjectToPlayer(BasicDBObject obj)
	{
		setJson(obj.toString());
		data = convertDBObjectToObject(obj, cols);
	}		
	
	/**
	 * generates string for log entry
	 * @return
	 */
	public String getLogEntry()	
	{ 
		return generateLogEntry(data, "Player"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Player")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }
	public String getTeamId() { return data.get("teamId").toString(); }
	public String getFirstName() { return data.get("firstName").toString(); }
	public String getLastName() { return data.get("lastName").toString(); }
	public String getEmail() { return data.get("firstName").toString(); }	

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }
	public void setTeamId(String teamId) { data.put("teamId",teamId); }
	public void setFirstName(String firstName) { data.put("firstName",firstName); }
	public void setLastName(String lastName) { data.put("lastName",lastName); }
	public void setEmail(String email) { data.put("firstName",email); }	
	
}
