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

public class TeamAnswerManager 
{

	private final String COLLECTION_NAME = "team_answers";
	
	/**
	 * 
	 */
	public TeamAnswerManager() 
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
	public TeamAnswer upsert(TeamAnswer teamAnswer, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", teamAnswer.getId()), 
				teamAnswer.convertTeamAnswerToBasicDBObject(), true, false);
		return findOne(teamAnswer.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public TeamAnswer findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		TeamAnswer teamAnswer = new TeamAnswer();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				teamAnswer.convertDBObjectToTeamAnswer(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return teamAnswer;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<TeamAnswer> getAllForTeamLocation(TeamLocation teamLocation, DB db)
	{
		ArrayList<TeamAnswer> team = new ArrayList<TeamAnswer>();

		BasicDBObject query = new BasicDBObject("teamLocationId", teamLocation.getId());
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
			   team.add(new TeamAnswer(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return team;
	}
		
}
