package hunt.db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Hunt;
import hunt.beans.Location;
import hunt.beans.Team;

public class TeamManager 
{

	private final String COLLECTION_NAME = "teams";
	
	/**
	 * 
	 */
	public TeamManager() 
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
	public Team upsert(Team team, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", team.getId()), 
			    team.convertTeamToBasicDBObject(), true, false);
		return findOne(team.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Team findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Team team = new Team();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				team.convertDBObjectToTeam(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return team;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Team> getAllForHunt(Hunt hunt, DB db)
	{
		ArrayList<Team> team = new ArrayList<Team>();

		BasicDBObject query = new BasicDBObject("huntId", hunt.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   team.add(new Team(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return team;
	}
		
}
