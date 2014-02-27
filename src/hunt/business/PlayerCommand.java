package hunt.business;

import com.mongodb.DB;

import hunt.beans.Player;
import hunt.beans.Team;
import hunt.db.PlayerManager;
import hunt.db.TeamManager;

public class PlayerCommand 
{

	public PlayerCommand()
	{
		
	}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param teamId
	 * @param db
	 * @return
	 */
	public boolean addPlayerToTeam(String firstName, String lastName, String email, String phoneNumber, String teamId, DB db)
	{
		boolean added = true;
		
		Team team = new TeamManager().findOne(teamId, db);
		
		Player player = new Player();
		player.setTeamId(teamId);
		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setEmail(email);
		player.setPhoneNumber(phoneNumber);
		
		try
		{
			if (validatePlayer(player))
			{
				new PlayerManager().upsert(player, db);
			}
		}
		catch (Exception e)
		{
			added = false;
		}
		
		return added;
	}
	
	public boolean movePlayer(String playerId, String oldTeamId, String newTeamId, DB db)
	{
		boolean moved = true;
		
		try
		{
			PlayerManager pMgr = new PlayerManager();
			Player player = pMgr.getPlayerOnTeam(playerId, oldTeamId, db);
			player.setTeamId(newTeamId);
			pMgr.upsert(player, db);
		}
		catch (Exception e)
		{
			
		}
		
		return moved;
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
