package hunt.business;

import java.sql.Connection;

import hunt.beans.Account;
import hunt.beans.Hunt;
import hunt.beans.Location;
import hunt.beans.Question;
import hunt.db.QuestionManager;

import com.mongodb.DB;

public class QuestionCommand extends Command
{
	
	private QuestionManager mgr = new QuestionManager();
	
	public QuestionCommand()
	{
		
	}
	
	// "id", "locationId", "question", "answer", "points"}
	
	public Question updateQuestion(String questionId, String huntId, String locationId, String question, String answer, String points, DB db)
	{
		Question q = new Question(questionId, locationId, question, answer, points);
		
		return q;
	}
	
	public void deleteQuestionsForLocation(String locationId, DB db)
	{
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception
	{
		System.out.println("start test");
	
		// get connection through command
		System.out.println("   getting connection...");
		Connection c = getMySqlConnection();

		// create account
		System.out.println("   create account...");
		Account a = new AccountCommand().insertAccount(c, "test", "account", "testaccount@m.com", "1112223333", "test", "test");
		
		// create hunt 
		System.out.println("   create hunt...");
		Hunt h = new HuntCommand().insertHunt(c, Integer.parseInt(a.getId()), "test_hunt", "2/1/2014 12:00:00");
		
		// create location
		System.out.println("   create location...");
		Location l = new LocationCommand().insertLocation(c, h.getId(), "name", "code", "key", "address", "1112223333", "", "N");
		
		// create question object
		System.out.println("   setting up object...");
		Question q1 = new Question("", l.getId(), "What is your favorite color?", "Red", "10");
		q1.show();
		
		// insert question
		System.out.println("   inserting question...");
		int id = mgr.insert(c, q1);
		q1.setId(String.valueOf(id));
		
		// get location
		System.out.println("   getting question...");
		Question q2 = mgr.get(c, q1);
		q2.show();
		
		// update location
		System.out.println("   updating question...");
		q2.setQuestion("How old are you? U");
		q2.setAnswer("10 U");
		q2.setPoints("40");
		mgr.update(c, q2);
		
		// get question
		System.out.println("   getting question...");
		Question q3 = mgr.get(c, q2);
		q3.show();
		
		// delete question
		System.out.println("   deleting question...");
		mgr.delete(c, q3);
		
		// delete location
		System.out.println("   delete hunt...");
		new LocationCommand().deleteLocation(c, Integer.parseInt(l.getId()));		
		
		// delete hunt
		System.out.println("   delete hunt...");
		new HuntCommand().deleteHunt(c, Integer.parseInt(h.getId()));
		
		// delete account
		System.out.println("   delete account...");
		new AccountCommand().deleteAccount(c, Integer.parseInt(a.getId()));
		
		// closing connection
		System.out.println("   closing connection...");
		c.close();
		
		System.out.println("end test");			
	}	
		
	
}
