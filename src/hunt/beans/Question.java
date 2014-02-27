package hunt.beans;

import java.util.Hashtable;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Question extends BeanUtils 
{
	
	/**
	 * empty constructor
	 */
	public Question()
	{
	}
	
	/**
	 * populates a Question object from a BasicDBObject  
	 */
	public Question(DBObject obj) 
	{
		convertDBObjectToQuestion(obj);
	}	
	
	/**
	 * populates an Quesiton object from data passed
	 * @param id
	 */
	public Question(String id, String huntId, String locationId, String question, String answer, String points)
	{
		this.setId(id);
		this.setHuntId(huntId);
		this.setLocationId(locationId);
		this.setQuestion(question);
		this.setAnswer(answer);
		this.setPoints(points);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId", "locationId", "question", "answer", "points"};
	private Hashtable<String,Object> data;
	private String json;
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Question object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertQuestionToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Question
	 * @param obj
	 */
	public void convertDBObjectToQuestion(DBObject obj)
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
		return generateLogEntry(data, "Question"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Question")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }
	public String getLocationId() { return data.get("locationId").toString(); }
	public String getQuestion() { return data.get("question").toString(); }
	public String getAnswer() { return data.get("answer").toString(); }
	public String getPoints() { return data.get("points").toString(); }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }
	public void setLocationId(String locationId) { data.put("locationId",locationId); }
	public void setQuestion(String question) { data.put("question", question); }
	public void setAnswer(String answer) { data.put("ansswer",answer); }
	public void setPoints(String points) { data.put("points",points); }

}
