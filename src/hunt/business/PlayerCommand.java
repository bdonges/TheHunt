package hunt.business;

import java.sql.Connection;
import java.util.Vector;

import hunt.beans.Player;
import hunt.db.PlayerManager;
import hunt.utils.LoggerUtil;

public class PlayerCommand extends Command
{

	private PlayerManager mgr = new PlayerManager();
	
	public PlayerCommand()
	{
		
	}
	
	/**
	 * 
	 * @param con
	 * @param playerId
	 * @return
	 * @throws Exception
	 */
	public Player getPlayer(Connection con, String playerId) throws Exception
	{
		return mgr.get(con, new Player(playerId, "", "", "", "", ""));
	}
	
	
	/**
	 * 
	 * @param con
	 * @param teamId
	 * @return
	 * @throws Exception
	 */
	public Vector<Player> getPlayersForTeam(Connection con, String teamId) throws Exception
	{
		LoggerUtil.logToOut("PlayerCommand.getPlayersForTeam("+teamId+")");
		return mgr.getForTeam(con, new Player("", teamId, "", "", "", ""));
	}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param teamId
	 * @return
	 * @throws Exception 
	 */
	public Player addPlayer(Connection con, String firstName, String lastName, String email, String phoneNumber, String teamId) throws Exception
	{
		Player p = new Player("", teamId, firstName, lastName, email, phoneNumber);
		
		p.setId(String.valueOf(mgr.insert(con, p)));
		p = mgr.get(con, p);
		
		return p;
	}

	/**
	 * 
	 * @param con
	 * @param id
	 * @throws Exception
	 */
	public void deletePlayer(Connection con, String id) throws Exception
	{
		mgr.delete(con, new Player(id, "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @param newTeamId
	 * @return
	 * @throws Exception
	 */
	public Player updatePlayersTeam(Connection con, String id, String newTeamId) throws Exception
	{
		Player p = mgr.get(con, new Player(id, "", "", "", "", ""));
		p.setTeamId(newTeamId);
		mgr.updateTeam(con, p);
		return p;
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @param teamId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Player updatePlayer(Connection con, String id, String teamId, String firstName, String lastName, String email, String phone) throws Exception
	{
		mgr.update(con, new Player(id, teamId, firstName, lastName, email, phone));
		return mgr.get(con, new Player(id, "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public boolean validatePlayer(Player p)
	{
		boolean valid = true;
		
		if (p.getFirstName() == null ||
			p.getLastName() == null ||
			p.getEmail() == null)
		{
			valid = false;
		}
		
		return valid;
	}
	
}
