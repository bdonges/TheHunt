package hunt.beans;

import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;

public class Team extends BeanUtils 
{
	
	/**
	 * empty constructor
	 */
	public Team()
	{
	}
	
	/**
	 * populates a Team object from a BasicDBObject  
	 */
	public Team(BasicDBObject obj) 
	{
		convertDBObjectToTeam(obj);
	}	
	
	/**
	 * populates an Team object from data passed
	 * @param id
	 */
	public Team(String id, String huntId, String name)
	{
		this.setId(id);
		this.setHuntId(huntId);
		this.setName(name);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId","name"};
	private Hashtable<String,Object> data;
	private String json;
	private Vector<Player> players = new Vector<Player>();
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Team object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertTeamToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Team
	 * @param obj
	 */
	public void convertDBObjectToTeam(BasicDBObject obj)
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
		return generateLogEntry(data, "Team"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Team")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }
	public String getName() { return data.get("name").toString(); }
	public Vector<Player> getPlayers() { return this.players; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }
	public void setName(String name) { data.put("name",name); }
	public void setPlayers(Vector<Player> players) { this.players = players; }

}
