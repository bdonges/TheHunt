package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.TeamLocation;

public class TeamLocationManager 
{
	
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String GET_FOR_TEAM = "GET_FOR_TEAM";
	private String GET_FOR_LOCATION = "GET_FOR_LOCATION";
	
	private String INSERT_QRY = "INSERT INTO team_locations " +
			"(team_id, location_id, hunt_id, code_entered_date, code_entered, locked_out, questions_submitted_date, score) " +
			"VALUES " +
			"(?,?,Now(),?,?,?,?)";
	private String UPDATE_QRY = "UPDATE team_locations SET team_id = ?, location_id = ?, hunt_id = ?, code_entered_date = ?, code_entered = ?, " +
			"locked_out = ?, questions_submitted_date = ?, score WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM team_locations WHERE id = ?";
	private String GET_QRY = "SELECT * FROM team_locations WHERE id = ?";
	private String GET_FOR_TEAM_QRY = "SELECT * FROM team_locations WHERE team_id = ? ORDER BY location_id";
	private String GET_FOR_LOCATION_QRY = "SELECT * FROM team_locations WHERE location_id = ? ORDER BY team_id";
	
	private Vector<TeamLocation> process(ResultSet rs) throws Exception
	{
		Vector<TeamLocation> objs = new Vector<TeamLocation>();
		while (rs.next())
		{
			objs.add(new TeamLocation(rs.getString("id"),
								  	  rs.getString("team_id"),
								  	  rs.getString("location_id"),
								  	  rs.getString("hunt_id"),
								  	  rs.getString("code_entered_date"),
								  	  rs.getString("code_entered"),
								  	  rs.getString("locked_out"),
								  	  rs.getString("questions_sumbmitted_date"),
								  	  rs.getString("score")));
		}
		return objs;
	}
	
	private Vector<TeamLocation> executeSql(Connection c, String sql, String action, TeamLocation obj) throws Exception
	{
		// instantiate list object
		Vector<TeamLocation> objs = new Vector<TeamLocation>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamId()));
			pst.setInt(2, Integer.parseInt(obj.getLocationId()));
			pst.setInt(3, Integer.parseInt(obj.getHuntId()));
			pst.setString(4, obj.getCodeEnteredDate());
			pst.setString(5, obj.getCodeEntered());
			pst.setString(6, obj.getLockedOut());
			pst.setString(7, obj.getQuestionsSubmittedDate());
			pst.setString(8, obj.getCodeEnteredDate());
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamId()));
			pst.setInt(2, Integer.parseInt(obj.getLocationId()));
			pst.setInt(3, Integer.parseInt(obj.getHuntId()));
			pst.setString(4, obj.getCodeEnteredDate());
			pst.setString(5, obj.getCodeEntered());
			pst.setString(6, obj.getLockedOut());
			pst.setString(7, obj.getQuestionsSubmittedDate());
			pst.setString(8, obj.getCodeEnteredDate());
			pst.setInt(9, Integer.parseInt(obj.getId()));
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
		else if (action.equals(GET_FOR_TEAM))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(GET_FOR_LOCATION))
		{
			pst.setInt(1, Integer.parseInt(obj.getLocationId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}		
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<TeamLocation>();
		
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
	public int insert(Connection c, TeamLocation obj) throws Exception
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
	public void update(Connection c, TeamLocation obj) throws Exception
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
	public TeamLocation get(Connection c, TeamLocation obj) throws Exception
	{
		TeamLocation tl = new TeamLocation();
		
		Vector <TeamLocation> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			tl = objs.get(0);
		
		return tl;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamLocation> getForTeam(Connection c, TeamLocation obj) throws Exception
	{
		return executeSql(c, GET_FOR_TEAM_QRY, GET_FOR_TEAM, obj);
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<TeamLocation> getForLocation(Connection c, TeamLocation obj) throws Exception
	{
		return executeSql(c, GET_FOR_LOCATION_QRY, GET_FOR_LOCATION, obj);
	}

	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, TeamLocation obj) throws Exception
	{
		executeSql(c, DELETE_QRY, DELETE, obj);
	}
			
		
}
