package hunt.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import hunt.beans.Hunt;
import hunt.beans.Team;
import hunt.beans.TeamLocation;
import hunt.beans.TeamAnswer;
import hunt.db.QuestionManager;
import hunt.db.TeamAnswerManager;
import hunt.db.TeamLocationManager;

import com.mongodb.DB;

public class TeamResultsCommand extends Command
{

	public TeamResultsCommand() 
	{
		
	}
	
	/**
	 * 
	 * @param team
	 * @param hunt
	 * @param db
	 * @return
	 */
	public Team getTeamResultsForHunt(Connection con, Team team, Hunt hunt)
	{

		return team;
	}
	
	/**
	 * 
	 * @param tl
	 * @param db
	 * @return
	 */
	public TeamLocation getLocationAndAnswers(Connection con, TeamLocation tl)
	{
		return tl;
	}
	
	/**
	 * 
	 * @param answer
	 * @param db
	 * @return
	 */
	public TeamAnswer getTeamAnswerWithQuestion(Connection con, TeamAnswer answer)
	{
		return answer;
	}
}
