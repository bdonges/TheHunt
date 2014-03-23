package hunt.business;

import java.sql.Connection;
import java.util.Vector;

import hunt.beans.Account;
import hunt.beans.Hunt;
import hunt.beans.Location;
import hunt.beans.Question;
import hunt.db.QuestionManager;

public class QuestionCommand extends Command
{
	
	private QuestionManager mgr = new QuestionManager();
	
	public QuestionCommand()
	{
		
	}

	/**
	 * 
	 * @param con
	 * @param id
	 * @param answerEntered
	 * @return
	 * @throws Exception
	 */
	public int scoreAnswer(Connection con, String id, String answerEntered) throws Exception
	{
		int points = 0;
		Question q = mgr.get(con, new Question(id, "", "", "", "", ""));
		
		if (answerEntered.toLowerCase().trim().equals(q.getAnswer().toLowerCase().trim()))
			points = Integer.parseInt(q.getPoints());
		
		return points;
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Question getQuestion(Connection con, String id) throws Exception
	{
		return mgr.get(con, new Question(id, "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	public Vector<Question> getQuestionsForLocation(Connection con, String locationId) throws Exception
	{
		return mgr.getQuestionsForLocation(con, new Question("", locationId, "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @param locationId
	 * @param question
	 * @param answer
	 * @param points
	 * @param questionOrder
	 * @return
	 * @throws Exception
	 */
	public Question insertQuestion(Connection con, 
			   String huntId, 
			   String locationId, 
			   String question, 
			   String answer, 
			   String points,
			   String questionOrder) throws Exception
    {
		int id = mgr.insert(con, new Question("", locationId, question, answer, points, questionOrder));
		return mgr.get(con, new Question(String.valueOf(id), "", "", "", "", ""));
    }	
	
	/**
	 * 
	 * @param con
	 * @param questionId
	 * @param huntId
	 * @param locationId
	 * @param question
	 * @param answer
	 * @param points
	 * @return
	 * @throws Exception
	 */
	public Question updateQuestion(Connection con, 
								   String id, 
								   String huntId, 
								   String locationId, 
								   String question, 
								   String answer, 
								   String points,
								   String questionOrder) throws Exception
	{
		Question q = new Question(id, locationId, question, answer, points, questionOrder);
		mgr.update(con, q);
		return mgr.get(con, q);
	}
	
	/**
	 * 
	 * @param con
	 * @param locationId
	 * @throws Exception
	 */
	public void deleteQuestionsForLocation(Connection con, String locationId) throws Exception
	{
		mgr.deleteForLocation(con, new Question("", locationId, "", "", "", ""));
	}

	/**
	 * 
	 * @param con
	 * @param id
	 * @throws Exception
	 */
	public void deleteQuestion(Connection con, String id) throws Exception
	{
		mgr.delete(con, new Question(id, "", "", "", "", ""));
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
		Question q1 = new Question("", l.getId(), "What is your favorite color?", "Red", "10", "1");
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
		q2.setQuestionOrder("2");
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
