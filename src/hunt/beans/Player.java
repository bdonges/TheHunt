package hunt.beans;

import java.util.Hashtable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
	public Player(DBObject obj) 
	{
		convertDBObjectToPlayer(obj);
	}	
	
	/**
	 * populates an Player object from data passed
	 * @param id
	 */
	public Player(String id, String teamId, String firstName, String lastName, String email, String phoneNumber)
	{
		this.setId(id);
		this.setTeamId(teamId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id","teamId","firstName","lastName","email","phoneNumber"};
	private Hashtable<String,Object> data = new Hashtable<String,Object>();
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
	public void convertDBObjectToPlayer(DBObject obj)
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
	public String getTeamId() { return data.get("teamId").toString(); }
	public String getFirstName() { return data.get("firstName").toString(); }
	public String getLastName() { return data.get("lastName").toString(); }
	public String getEmail() { return data.get("firstName").toString(); }	
	public String getPhoneNumber() { return data.get("phoneNumber").toString();}

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setTeamId(String teamId) { data.put("teamId",teamId); }
	public void setFirstName(String firstName) { data.put("firstName",firstName); }
	public void setLastName(String lastName) { data.put("lastName",lastName); }
	public void setEmail(String email) { data.put("firstName",email); }	
	public void setPhoneNumber(String phoneNumber) { data.put("firstName", phoneNumber); }
	
}
