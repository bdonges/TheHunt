package hunt.business;

import java.sql.Connection;
import java.util.Vector;

import hunt.beans.TeamAnswer;
import hunt.db.TeamAnswerManager;


public class TeamAnswerCommand extends Command
{

	private TeamAnswerManager mgr = new TeamAnswerManager();
	
	public TeamAnswerCommand() 
	{
		
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @param teamLocationId
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamAnswer> getAnswersForTeamForLocation(Connection con, String teamLocationId) throws Exception
	{
		return mgr.getForTeamLocation(con, new TeamAnswer("", teamLocationId, "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param teamLocationId
	 * @return
	 * @throws Exception
	 */
	public int getScoreForTeamLocation(Connection con, String teamLocationId) throws Exception
	{
		Vector<TeamAnswer> answers = getAnswersForTeamForLocation(con, teamLocationId);
		int score = 0;
		for (TeamAnswer answer : answers)
		{
			score = Integer.parseInt(answer.getScore()) + score;
		}
		return score;
	}
	
	/**
	 * 
	 * @param con
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamAnswer> getAnswersForQuestion(Connection con, String questionId) throws Exception
	{
		return mgr.getForQuestion(con, new TeamAnswer("", "", questionId, "", "")); 
	}
	
	/**
	 * 
	 * @param con
	 * @param teamAnswerId
	 * @param questionId
	 * @param answer
	 * @param score
	 * @return
	 * @throws Exception
	 */
	public TeamAnswer insertTeamAnswer(Connection con, String teamLocationId, String questionId, String answer) throws Exception
	{
		String score = String.valueOf(new QuestionCommand().scoreAnswer(con, questionId, answer));
		int id = mgr.insert(con, new TeamAnswer("", teamLocationId, questionId, answer, score));
		return mgr.get(con, new TeamAnswer(String.valueOf(id), "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @throws Exception
	 */
	public void deleteTeamAnswer(Connection con, String id) throws Exception
	{
		mgr.delete(con, new TeamAnswer(String.valueOf("id"), "", "", "", ""));
	}
}
