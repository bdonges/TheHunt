package hunt.business;

import com.mongodb.DB;

import hunt.beans.Player;
import hunt.beans.Team;
import hunt.db.PlayerManager;
import hunt.db.TeamManager;

public class PlayerCommand extends Command
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
		

		return added;
	}
	
	public boolean movePlayer(String playerId, String oldTeamId, String newTeamId, DB db)
	{
		boolean moved = true;
		

		
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
