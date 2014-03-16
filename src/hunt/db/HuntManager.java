package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.Hunt;

public class HuntManager 
{

	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String GET_FOR_ACCOUNT = "GET_FOR_ACCOUNT";
	
	private String INSERT_QRY = "INSERT INTO hunts (account_id,name,run_date) values (?,?,?)";
	private String UPDATE_QRY = "UPDATE hunts SET account_id = ?, name = ?, run_date = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM hunts WHERE id = ?";
	private String GET_QRY = "SELECT * FROM hunts WHERE id = ?";
	private String GET_FOR_ACCOUNT_QRY = "SELECT * FROM hunts WHERE account_id = ? ORDER BY run_date";
	
	private Vector<Hunt> process(ResultSet rs) throws Exception
	{
		Vector<Hunt> objs = new Vector<Hunt>();
		while (rs.next())
		{
			objs.add(new Hunt(rs.getString("id"), 
					          rs.getString("accountId"), 
					          rs.getString("name"), 
					          rs.getString("run_date")));
		}
		return objs;
	}
	
	private Vector<Hunt> executeSql(Connection c, String sql, String action, Hunt obj) throws Exception
	{
		// instantiate list object
		Vector<Hunt> objs = new Vector<Hunt>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getAccountId()));
			pst.setString(2, obj.getName());
			pst.setString(3, obj.getRunDate());
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setInt(1, Integer.parseInt(obj.getAccountId()));
			pst.setString(2, obj.getName());
			pst.setString(3, obj.getRunDate());
			pst.setInt(4, Integer.parseInt(obj.getId()));
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
		else if (action.equals(GET_FOR_ACCOUNT))
		{
			pst.setInt(1, Integer.parseInt(obj.getAccountId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Hunt>();
		
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
	public int insert(Connection c, Hunt obj) throws Exception
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
	public void update(Connection c, Hunt obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Hunt obj) throws Exception
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
	public Hunt get(Connection c, Hunt obj) throws Exception
	{
		Hunt h = new Hunt();
		
		Vector <Hunt> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			h = objs.get(0);
		
		return h;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<Hunt> getForAccount(Connection c, Hunt obj) throws Exception
	{
		return executeSql(c, GET_FOR_ACCOUNT_QRY, GET_FOR_ACCOUNT, obj);
	}	
}
