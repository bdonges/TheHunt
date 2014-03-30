package hunt.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

	private TeamLocationManager mgr = new TeamLocationManager();
	
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
	public Team getTeamResultsForHunt(Connection con, Team team, Hunt hunt) throws Exception
	{
		Vector<TeamLocation> tmp = mgr.getForTeam(con, new TeamLocation("", team.getId(), "", "", "", "", "", "", ""));
		Vector<TeamLocation> locations = new Vector<TeamLocation>();
		
		int score = 0;
		
		for (TeamLocation location : tmp)
		{
			score = Integer.parseInt(location.getScore()) + score;
			
			location = getAnswersForLocation(con, location);
			locations.add(location);
		}
		team.setTeamLoations(locations);
		team.setScore(String.valueOf(score));
		return team;
	}
	
	/**
	 * 
	 * @param tl
	 * @param db
	 * @return
	 */
	public TeamLocation getAnswersForLocation(Connection con, TeamLocation tl) throws Exception
	{
		tl.setTeamAnswers(new TeamAnswerCommand().getAnswersForTeamForLocation(con, tl.getId()));
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
