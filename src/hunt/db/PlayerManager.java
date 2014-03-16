package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.Player;

public class PlayerManager 
{
	
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String GET_FOR_TEAM = "GET_FOR_TEAM";
	
	private String INSERT_QRY = "INSERT INTO players (team_id, first_name, last_name, email, phone_number) values (?,?,?,?,?)";
	private String UPDATE_QRY = "UPDATE players SET team_id = ?, first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM players WHERE id = ?";
	private String GET_QRY = "SELECT * FROM players WHERE id = ?";
	private String GET_FOR_TEAM_QRY = "SELECT * FROM players WHERE team_id = ? ORDER BY last_name, first_name";
	
	private Vector<Player> process(ResultSet rs) throws Exception
	{
		Vector<Player> objs = new Vector<Player>();
		while (rs.next())
		{
			objs.add(new Player(rs.getString("id"), 
							  rs.getString("team_id"), 
							  rs.getString("first_name"), 
							  rs.getString("last_name"), 
							  rs.getString("email"),
							  rs.getString("phone_number")));
		}
		return objs;
	}
	
	private Vector<Player> executeSql(Connection c, String sql, String action, Player obj) throws Exception
	{
		// instantiate list object
		Vector<Player> objs = new Vector<Player>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getTeamId()));
			pst.setString(2, obj.getFirstName());
			pst.setString(3, obj.getLastName());
			pst.setString(4, obj.getEmail());
			pst.setString(5, obj.getPhoneNumber());
			
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
			pst.setString(2, obj.getFirstName());
			pst.setString(3, obj.getLastName());
			pst.setString(4, obj.getEmail());
			pst.setString(5, obj.getPhoneNumber());			
			pst.setInt(6, Integer.parseInt(obj.getId()));
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
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Player>();
		
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
	public int insert(Connection c, Player obj) throws Exception
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
	public void update(Connection c, Player obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Player obj) throws Exception
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
	public Player get(Connection c, Player obj) throws Exception
	{
		Player p = new Player();
		
		Vector <Player> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			p = objs.get(0);
		
		return p;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Player getForTeam(Connection c, Player obj) throws Exception
	{
		Player p = new Player();
		
		Vector <Player> objs = executeSql(c, GET_FOR_TEAM_QRY, GET_FOR_TEAM, obj);
		
		if (objs != null && objs.size() > 0)
			p = objs.get(0);
		
		return p;
	}
	

}
