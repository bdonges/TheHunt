package hunt.db;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Hunt;
import hunt.beans.Player;
import hunt.beans.Team;

public class PlayerManager 
{

	private final String COLLECTION_NAME = "players";
	
	/**
	 * 
	 */
	public PlayerManager() 
	{
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public DBCollection getCollection(DB db)
	{
		return db.getCollection(COLLECTION_NAME);
	}
	
	/**
	 * 
	 * @param account
	 * @param db
	 * @return
	 */
	public Player upsert(Player player, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", player.getId()), 
				player.convertPlayerToBasicDBObject(), true, false);
		return findOne(player.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Player findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Player player = new Player();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				player.convertDBObjectToPlayer(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return player;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Player> getAllPlayersOnTeam(Team team, DB db)
	{
		ArrayList<Player> players = new ArrayList<Player>();
		
		BasicDBObject query = new BasicDBObject("teamId", team.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   players.add(new Player(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return players;
	}

	/**
	 * 
	 * @param playerId
	 * @param teamId
	 * @param db
	 * @return
	 */
	public Player getPlayerOnTeam(String playerId, String teamId, DB db)
	{
		Player player = new Player();
		
		BasicDBObject query = new BasicDBObject("teamId", teamId).append("_id", playerId);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   player = new Player(cursor.next());
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return player;
	}	
	
	/**
	 * 
	 * @param hunt
	 * @param db
	 * @return
	 */
	public ArrayList<Player> getAllPlayersInHunt(Hunt hunt, DB db)
	{
		ArrayList<Player> players = new ArrayList<Player>();
		
		// get teams and for each team, get players
		List<Team> teams = new TeamManager().getAllForHunt(hunt, db);		
		if (teams != null && teams.size() > 1)
		{
			for (Team team : teams)
			{
				BasicDBObject query = new BasicDBObject("teamId", team.getId());
				DBCollection col = getCollection(db);
				DBCursor cursor = col.find(query);
				
				try 
				{	
				   while(cursor.hasNext()) 
				   {
					   players.add(new Player(cursor.next()));
			   }
				} 
				finally 
				{
				   cursor.close();
				}
			}
		}
		return players;
	}

	
}
