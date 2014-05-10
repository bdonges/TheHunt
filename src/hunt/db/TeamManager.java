package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.Team;

public class TeamManager 
{
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String GET = "GET";
	private String GET_FOR_HUNT = "GET_FOR_HUNT";
	private String DELETE = "DELETE";
	private String LOGIN_FOR_HUNT = "LOGIN_FOR_HUNT";
	
	private String INSERT_QRY = "INSERT INTO teams (hunt_id, name, score, password) VALUES (?,?,?,?)";
	private String UPDATE_QRY = "UPDATE teams SET hunt_id = ?, name = ?, score = ?, password = ? WHERE id = ?";
	private String GET_QRY = "SELECT * FROM teams WHERE id = ?";
	private String GET_FOR_HUNT_QRY = "SELECT * FROM teams WHERE hunt_id = ? ORDER BY name";
	private String DELETE_QRY = "DELETE FROM teams WHERE id = ?";
	private String LOGIN_FOR_HUNT_QRY = "SELECT * FROM teams WHERE name = ? AND password = ? AND hunt_id = ?";
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Vector<Team> process(ResultSet rs) throws Exception
	{
		Vector<Team> objs = new Vector<Team>();
		while (rs.next())
		{
			objs.add(new Team(rs.getString("id"), 
							  rs.getString("hunt_id"), 
							  rs.getString("name"), 
							  rs.getString("score"), 
							  rs.getString("password")));
		}
		return objs;
	}
	
	/**
	 * 
	 * @param c
	 * @param sql
	 * @param action
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private Vector<Team> executeSql(Connection c, String sql, String action, Team obj) throws Exception
	{
		if (c.isClosed())
			throw new Exception("Connection is closed");
		
		// instantiate list object
		Vector<Team> objs = new Vector<Team>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getHuntId()));
			pst.setString(2, obj.getName());
			pst.setInt(3, Integer.parseInt(obj.getScore()));
			pst.setString(4, obj.getPassword());
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setInt(1, Integer.parseInt(obj.getHuntId()));
			pst.setString(2, obj.getName());
			pst.setInt(3, Integer.parseInt(obj.getScore()));
			pst.setInt(4, Integer.parseInt(obj.getId()));
			pst.setString(5, obj.getPassword());
			pst.execute();
		}		
		else if (action.equals(GET))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(GET_FOR_HUNT))
		{
			pst.setInt(1, Integer.parseInt(obj.getHuntId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(DELETE))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			pst.execute();
		}
		else if (action.equals(LOGIN_FOR_HUNT))
		{
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getPassword());
			pst.setInt(3, Integer.parseInt(obj.getHuntId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}		
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Team>();
		
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
	public int insert(Connection c, Team obj) throws Exception
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
	public void update(Connection c, Team obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Team obj) throws Exception
	{
		executeSql(c, DELETE_QRY, DELETE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Team get(Connection c, Team obj) throws Exception
	{
		Team t = new Team();
		
		Vector <Team> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			t = objs.get(0);
		
		return t;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<Team> getForHunt(Connection c, Team obj) throws Exception
	{
		return executeSql(c, GET_FOR_HUNT_QRY, GET_FOR_HUNT, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Team loginForHunt(Connection c, Team obj) throws Exception
	{
		Team t = new Team();
		
		Vector <Team> objs = executeSql(c, LOGIN_FOR_HUNT_QRY, LOGIN_FOR_HUNT, obj);
		
		if (objs != null && objs.size() > 0)
			t = objs.get(0);
		
		return t;
	}	
}
