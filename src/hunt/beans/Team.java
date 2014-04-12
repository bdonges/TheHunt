package hunt.beans;

import java.util.Vector;
import java.util.Hashtable;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
	public Team(DBObject obj) 
	{
		convertDBObjectToTeam(obj);
	}	
	
	/**
	 * populates an Team object from data passed
	 * @param id
	 */
	public Team(String id, String huntId, String name, String score, String password)
	{
		this.setId(id);
		this.setHuntId(huntId);
		this.setName(name);
		this.setScore(score);
		this.setPassword(password);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId","name", "score", "password"};
	private Hashtable<String,Object> data = new Hashtable<String,Object>();
	private String json;
	
	private Vector<TeamLocation> teamLocations = new Vector<TeamLocation>();
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
	public void convertDBObjectToTeam(DBObject obj)
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
	public String getScore() { return data.get("score").toString(); }
	public String getPassword() { return data.get("password").toString(); }
	public Vector<TeamLocation> getTeamLocations() { return teamLocations; }
	public Vector<Player> getPlayers() { return players; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }
	public void setName(String name) { data.put("name",name); }
	public void setScore(String score) { data.put("score", score); }
	public void setPassword(String password) { data.put("password", password); }
	public void setTeamLoations(Vector<TeamLocation> teamLocations) { this.teamLocations = teamLocations; }
	public void setPlayers(Vector<Player> players) { this.players = players; }

}
