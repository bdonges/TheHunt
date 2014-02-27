package hunt.business;

import hunt.beans.Hunt;
import hunt.beans.Team;
import hunt.db.HuntManager;
import hunt.db.TeamManager;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;

public class TeamCommand 
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
	public List<Team> getTeams(String huntId, DB db)
	{
		List<Team> teams = new ArrayList<Team>();
		
		try
		{
			Hunt hunt = new HuntManager().findOne(huntId, db);
			teams = new TeamManager().getAllForHunt(hunt, db);
		
			if (teams == null)
				teams = new ArrayList<Team>();
		}
		catch (Exception e)
		{
			
		}
		return teams;
	}
	
	/**
	 * 
	 * @param teamId
	 * @param huntId
	 * @param db
	 * @return
	 */
	public List<Team> getAllTeamResultsForHunt(String teamId, String huntId, DB db)
	{
		List<Team> teamsWithResults = new ArrayList<Team>();
		
		try
		{
			Hunt hunt = new HuntManager().findOne(huntId, db);
			
			
			List<Team> teams = new TeamManager().getAllForHunt(hunt, db);
		
			if (teams != null && teams.size() > 0)
			{
				TeamResultsCommand trCmd = new TeamResultsCommand();
				for (Team team : teams)
				{
					team = trCmd.getTeamResultsForHunt(team, hunt, db);
					teamsWithResults.add(team);
				}
			}
		}
		catch (Exception e)
		{
			
		}
		return teamsWithResults;
	}
	
	
	
}
