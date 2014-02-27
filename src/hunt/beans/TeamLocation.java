package hunt.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TeamLocation extends BeanUtils 
{
	/**
	 * empty constructor
	 */
	public TeamLocation()
	{
	}
	
	/**
	 * populates a TeamLocation object from a BasicDBObject  
	 */
	public TeamLocation(DBObject obj) 
	{
		convertDBObjectToTeamLocation(obj);
	}	
	
	/**
	 * populates a TeamLocation object from data passed
	 * @param id
	 */
	public TeamLocation(String id, String teamId, String locationId, String huntId, String codeEnteredDate, 
			String codeEntered, String lockedOut, String questionsSubmittedDate, String score)
	{
		this.setId(id);
		this.setHuntId(huntId);

	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "teamId", "locationId", "huntId", "codeEnteredDate","codeEntered","lockedOut","questionsSubmittedDate","score"};
	private Hashtable<String,Object> data;
	private String json;
	
	private List<TeamAnswer> teamAnswers = new ArrayList<TeamAnswer>();
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a TeamLocation object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertTeamLocationToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a TeamLocation
	 * @param obj
	 */
	public void convertDBObjectToTeamLocation(DBObject obj)
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
		return generateLogEntry(data, "TeamLocation"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "TeamLocation")); 
	}

	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getTeamId() { return data.get("teamId").toString(); }
	public String getLocationId() { return data.get("locationId").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }
	public String getCodeEnteredDate() { return data.get("codeEnteredDate").toString(); }
	public String getCodeEntered() { return data.get("codeEntered").toString(); }
	public String getLockedOut() { return data.get("lockedOut").toString(); }
	public String getQuestionsSubmittedDate() { return data.get("questionsSubmittedDate").toString(); }
	public String getScore() { return data.get("score").toString(); }
	public List<TeamAnswer> getTeamAnswers() { return this.teamAnswers; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setTeamId(String teamId) { data.put("teamId",teamId); }
	public void setLocationId(String locationId) { data.put("locationId", locationId); }
	public void setHuntId(String huntId) { data.put("huntId", huntId); }
	public void setCodeEnteredDate(String codeEnteredDate) { data.put("codeEnteredDate", codeEnteredDate); }
	public void setCodeEntered(String codeEntered) { data.put("codeEntered", codeEntered); }
	public void setLockedOut(String lockedOut) { data.put("lockedOut", lockedOut); }
	public void setQuestionsSubmittedDate(String questionsSubmittedDate) { data.put("questionsSubmittedDate", questionsSubmittedDate); }
	public void setScore(String score) { data.put("score",score); }
	public void setTeamAnswers(List<TeamAnswer> teamAnswers) { this.teamAnswers = teamAnswers; }

}
