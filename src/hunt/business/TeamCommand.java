package hunt.business;

import hunt.beans.Hunt;
import hunt.beans.Team;
import hunt.db.TeamManager;

import java.sql.Connection;
import java.util.Vector;

public class TeamCommand extends Command
{
	
	private TeamManager mgr = new TeamManager();
	
	public TeamCommand()
	{
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @param name
	 * @param score
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Team insertTeam(Connection con, String huntId, String name, String score, String password) throws Exception
	{
		int id = mgr.insert(con, new Team("", huntId, name, score, password));
		return mgr.get(con, new Team(String.valueOf(id), "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @param huntId
	 * @param name
	 * @param score
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Team updateTeam(Connection con, String id, String huntId, String name, String score, String password) throws Exception
	{
		mgr.update(con, new Team(id, huntId, name, score, password));
		return mgr.get(con, new Team(id, "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @throws Exception
	 */
	public void deleteTeam(Connection con, String id) throws Exception
	{
		mgr.delete(con, new Team(id, "", "", "", ""));
	}
	
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int login(Connection con, String huntId, String name, String password) throws Exception
	{
		Team team = mgr.loginForHunt(con, new Team("", huntId, name, "", password));
		
		int id = 0;
		if (team.getId() != null && !team.getId().equals("") && (Integer.parseInt(team.getId()) > 0))
			id = Integer.parseInt(team.getId());
		
		return id;
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Team getTeam(Connection con, String id) throws Exception
	{
		return mgr.get(con, new Team(id, "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @return
	 */
	public Vector<Team> getTeamsForHunt(Connection con, String huntId) throws Exception
	{
		return mgr.getForHunt(con, new Team("", huntId, "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param teamId
	 * @param huntId
	 * @return
	 */
	public Vector<Team> getAllTeamResultsForHunt(Connection con, String huntId) throws Exception
	{
		Vector<Team> teamsWithResults = new Vector<Team>();
		Vector<Team> teams = getTeamsForHunt(con, huntId);
		
		Hunt hunt = new HuntCommand().getHunt(con, huntId);
		
		TeamResultsCommand trcmd = new TeamResultsCommand();
		
		for (Team team : teams)
		{
			team = trcmd.getTeamResultsForHunt(con, team, hunt);
			teamsWithResults.add(team);
		}
		
		return teamsWithResults;
	}
	
	
	
}
