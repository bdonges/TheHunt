package hunt.beans;

import java.util.Hashtable;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TeamAnswer extends BeanUtils 
{
	
	/**
	 * empty constructor
	 */
	public TeamAnswer()
	{
	}
	
	/**
	 * populates a TeamAnswer object from a BasicDBObject  
	 */
	public TeamAnswer(DBObject obj) 
	{
		convertDBObjectToTeamAnswer(obj);
	}	
	
	/**
	 * populates an TeamLocation object from data passed
	 * @param id
	 */
	public TeamAnswer(String id, String teamLocationId, String questionId, String answer, String score)
	{
		this.setId(id);
		this.setTeamLocationId(teamLocationId);
		this.setQuestionId(questionId);
		this.setAnswer(answer);
		this.setScore(score);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "teamLocationId", "questionId", "answer", "score"};
	private Hashtable<String,Object> data;
	private String json;
	
	private Question question = new Question();
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a TeamAnswer object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertTeamAnswerToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a TeamAnswer
	 * @param obj
	 */
	public void convertDBObjectToTeamAnswer(DBObject obj)
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
		return generateLogEntry(data, "TeamAnswer"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "TeamAnswer")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getTeamLocationId() { return data.get("teamLocationId").toString(); }
	public String getQuestionId() { return data.get("questionId").toString(); }
	public String getAnswer() { return data.get("answer").toString(); }
	public String getScore() { return data.get("score").toString(); }
	public Question getQuestion() { return this.question; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setTeamLocationId(String teamLocationId) { data.put("teamLocationId",teamLocationId); }
	public void setQuestionId(String questionId) { data.put("questionId",questionId); }
	public void setAnswer(String answer) { data.put("answer", answer); }
	public void setScore(String score) { data.put("score",score); }
	public void setQuestion(Question question) { this.question = question; }

}
