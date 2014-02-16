package hunt.beans;

import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Hunt extends BeanUtils 
{
	
	/**
	 * empty constructor
	 */
	public Hunt()
	{
	}
	
	/**
	 * populates a Hunt object from a BasicDBObject  
	 */
	public Hunt(DBObject obj) 
	{
		convertDBObjectToHunt(obj);
	}	
	
	/**
	 * populates an Hunt object from data passed
	 * @param id
	 */
	public Hunt(String id, String accountId, String name, String runDate)
	{
		this.setId(id);
		this.setAccountId(accountId);
		this.setName(name);
		this.setRunDate(runDate);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "accountId","name","runDate"};
	private Hashtable<String,Object> data;
	private String json;
	private Vector<Team> teams = new Vector<Team>();
	private Vector<Location> locations = new Vector<Location>();
	private Account account;
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Hunt object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertHuntToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Hunt
	 * @param obj
	 */
	public void convertDBObjectToHunt(DBObject obj)
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
		return generateLogEntry(data, "Hunt"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Hunt")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getAccountId() { return data.get("accountId").toString(); }
	public String getName() { return data.get("name").toString(); }
	public String getRunDate() { return data.get("runDate").toString(); }
	public Vector<Team> getTeams() { return this.teams; }
	public Vector<Location> getLocations() { return this.locations; }
	public Account getAccount() { return this.account; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setAccountId(String accountId) { data.put("accountId",accountId); }
	public void setName(String name) { data.put("name",name); }
	public void setRunDate(String runDate) { data.put("runDate",runDate); }
	public void setTeams(Vector<Team> teams) { this.teams = teams; }
	public void setLocations(Vector<Location> locations) { this.locations = locations; }
	public void setAccount(Account account) { this.account = account; }

}
