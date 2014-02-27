package hunt.db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Hunt;
import hunt.beans.Location;
import hunt.beans.Team;
import hunt.beans.TeamAnswer;
import hunt.beans.TeamLocation;

public class TeamLocationManager 
{

	private final String COLLECTION_NAME = "team_locations";
	
	/**
	 * 
	 */
	public TeamLocationManager() 
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
	public TeamLocation upsert(TeamLocation teamLocation, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", teamLocation.getId()), 
				teamLocation.convertTeamLocationToBasicDBObject(), true, false);
		return findOne(teamLocation.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public TeamLocation findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		TeamLocation teamLocation = new TeamLocation();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				teamLocation.convertDBObjectToTeamLocation(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return teamLocation;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<TeamLocation> getAllForTeam(Team team, DB db)
	{
		ArrayList<TeamLocation> teamLocations = new ArrayList<TeamLocation>();

		BasicDBObject query = new BasicDBObject("teamId", team.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   teamLocations.add(new TeamLocation(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return teamLocations;
	}
		
}
