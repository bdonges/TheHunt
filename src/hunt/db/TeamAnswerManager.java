package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.TeamAnswer;
import hunt.beans.TeamLocation;

public class TeamAnswerManager 
{
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String GET_FOR_TEAM_LOCATION = "GET_FOR_TEAM_LOCATION";
	private String GET_FOR_QUESTION = "GET_FOR_QUESTION";
	
	private String INSERT_QRY = "INSERT INTO team_answers (team_location_id, question_id, answer, score) values (?,?,?,?)";
	private String UPDATE_QRY = "UPDATE team_answers SET location_id = ?, question_id = ?, answer = ?, points = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM team_answers WHERE id = ?";
	private String GET_QRY = "SELECT * FROM team_answers WHERE id = ?";
	private String GET_FOR_TEAM_LOCATION_QRY = "SELECT * FROM team_answers WHERE location_id = ? ORDER BY question";
	private String GET_FOR_QUESTION_QRY = "SELECT * FROM team_answers WHERE question_id = ? ORDER BY score";
	
	private Vector<TeamAnswer> process(ResultSet rs) throws Exception
	{
		Vector<TeamAnswer> objs = new Vector<TeamAnswer>();
		while (rs.next())
		{
			objs.add(new TeamAnswer(rs.getString("id"),
								  rs.getString("team_location_id"),
								  rs.getString("question"),
								  rs.getString("answer"),
								  rs.getString("points")));
		}
		return objs;
	}
	
	private Vector<TeamAnswer> executeSql(Connection c, String sql, String action, TeamAnswer obj) throws Exception
	{
		// instantiate list object
		Vector<TeamAnswer> objs = new Vector<TeamAnswer>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamLocationId()));
			pst.setInt(2, Integer.parseInt(obj.getQuestionId()));
			pst.setString(3, obj.getAnswer());
			pst.setInt(4, Integer.parseInt(obj.getScore()));
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamLocationId()));
			pst.setInt(2, Integer.parseInt(obj.getQuestionId()));
			pst.setString(3, obj.getAnswer());
			pst.setInt(4, Integer.parseInt(obj.getScore()));
			pst.setInt(5, Integer.parseInt(obj.getId()));
			pst.execute();
		}		
		else if (action.equals(DELETE))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			pst.execute();
		}
		else if (action.equals(GET))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(GET_FOR_TEAM_LOCATION))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamLocationId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(GET_FOR_QUESTION))
		{
			pst.setInt(1, Integer.parseInt(obj.getQuestionId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}		
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<TeamAnswer>();
		
		return objs;
	}
	
	// *********************************************************************************
	// start public stuff
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public int insert(Connection c, TeamAnswer obj) throws Exception
	{
		executeSql(c, INSERT_QRY, INSERT, obj);
		return id;
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void update(Connection c, TeamAnswer obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public TeamAnswer get(Connection c, TeamAnswer obj) throws Exception
	{
		TeamAnswer ta = new TeamAnswer();
		
		Vector <TeamAnswer> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			ta = objs.get(0);
		
		return ta;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamAnswer> getForTeamLocation(Connection c, TeamAnswer obj) throws Exception
	{
		return executeSql(c, GET_FOR_TEAM_LOCATION_QRY, GET_FOR_TEAM_LOCATION, obj);
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamAnswer> getForQuestion(Connection c, TeamAnswer obj) throws Exception
	{
		return executeSql(c, GET_FOR_QUESTION_QRY, GET_FOR_QUESTION, obj);
	}

	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, TeamAnswer obj) throws Exception
	{
		executeSql(c, DELETE_QRY, DELETE, obj);
	}
			
		
}
