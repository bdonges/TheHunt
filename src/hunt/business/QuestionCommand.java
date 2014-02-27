package hunt.business;

import hunt.beans.Question;
import hunt.db.QuestionManager;

import com.mongodb.DB;

public class QuestionCommand 
{
	
	public QuestionCommand()
	{
		
	}
	
	// "id", "huntId", "locationId", "question", "answer", "points"}
	
	public Question upsertQuestion(String questionId, String huntId, String locationId, String question, String answer, String points, DB db)
	{
		Question q = new Question(questionId, huntId, locationId, question, answer, points);
		q = new QuestionManager().upsert(q, db);
		return q;
	}
	
	public void deleteQuestionsForLocation(String locationId, DB db)
	{
		
	}

}
