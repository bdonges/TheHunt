package hunt.business;

import hunt.beans.Hunt;
import hunt.beans.Team;
import hunt.db.HuntManager;
import hunt.db.TeamManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.mongodb.DB;

public class TeamCommand extends Command
{
	
	public TeamCommand()
	{
		
	}
	
	/**
	 * 
	 * @param huntId
	 * @param db
	 * @return
	 */
	public Vector<Team> getTeams(Connection con, String huntId)
	{
		Vector<Team> teams = new Vector<Team>();
		
		return teams;
	}
	
	/**
	 * 
	 * @param teamId
	 * @param huntId
	 * @param db
	 * @return
	 */
	public Vector<Team> getAllTeamResultsForHunt(String teamId, String huntId, DB db)
	{
		Vector<Team> teamsWithResults = new Vector<Team>();
		
		return teamsWithResults;
	}
	
	
	
}
