package hunt.db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Location;
import hunt.beans.Question;

public class QuestionManager 
{

	private final String COLLECTION_NAME = "questions";
	
	/**
	 * 
	 */
	public QuestionManager() 
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
	public Question upsert(Question question, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", question.getId()), 
			    question.convertQuestionToBasicDBObject(), true, false);
		return findOne(question.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Question findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Question question = new Question();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				question.convertDBObjectToQuestion(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return question;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Question> getAllForLocation(Location location, DB db)
	{
		ArrayList<Question> questions = new ArrayList<Question>();

		BasicDBObject query = new BasicDBObject("locationId", location.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
		       questions.add(new Question(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return questions;
	}
	
	public void deleteQuestion(Question question, DB db)
	{
		BasicDBObject query = new BasicDBObject("questionId", question.getId());
		DBCollection col = getCollection(db);
		col.findAndRemove(query);
	}
}
