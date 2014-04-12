package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.Location;
import hunt.utils.LoggerUtil;

public class LocationManager 
{
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String GET_FOR_HUNT = "GET_FOR_HUNT";
	
	private String INSERT_QRY = "INSERT INTO locations (hunt_id, name, code, key, address, phone, special_location_id, has_special) values (?,?,?,?,?,?,?)";
	private String UPDATE_QRY = "UPDATE locations SET name = ?, hunt_id = ?, code = ?, key = ?, address = ?, phone = ?, special_location_id = ?, has_special = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM locations WHERE id = ?";
	private String GET_QRY = "SELECT * FROM locations WHERE id = ?";
	private String GET_FOR_HUNT_QRY = "SELECT * FROM locations WHERE hunt_id = ? ORDER BY name";
	
	private Vector<Location> process(ResultSet rs) throws Exception
	{
		Vector<Location> objs = new Vector<Location>();
		while (rs.next())
		{
			objs.add(new Location(rs.getString("id"),
					              rs.getString("hunt_id"),
					              rs.getString("name"),
					              rs.getString("code"),
					              rs.getString("key"),
					              rs.getString("address"),
					              rs.getString("phone"),
					              rs.getString("special_location_id"),
					              rs.getString("has_special")));
		}
		return objs;
	}
	
	private Vector<Location> executeSql(Connection c, String sql, String action, Location obj) throws Exception
	{
		// instantiate list object
		Vector<Location> objs = new Vector<Location>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getHuntId()));
			pst.setString(2, obj.getName());
			pst.setString(3, obj.getCode());
			pst.setString(4, obj.getKey());
			pst.setString(5, obj.getAddress());
			pst.setString(6, obj.getPhoneNumber());
			pst.setInt(7,  Integer.parseInt(obj.getSpecialLocationId()));
			pst.setString(8, obj.getHasSpecial());
			
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
			pst.setString(3, obj.getCode());
			pst.setString(4, obj.getKey());
			pst.setString(5, obj.getAddress());
			pst.setString(6, obj.getPhoneNumber());
			pst.setInt(7,  Integer.parseInt(obj.getSpecialLocationId()));
			pst.setString(8, obj.getHasSpecial());
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
		else if (action.equals(GET_FOR_HUNT))
		{
			pst.setInt(1, Integer.parseInt(obj.getHuntId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Location>();
		
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
	public int insert(Connection c, Location obj) throws Exception
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
	public void update(Connection c, Location obj) throws Exception
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
	public Location get(Connection c, Location obj) throws Exception
	{
		Location l = new Location();
		
		Vector <Location> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			l = objs.get(0);
		
		return l;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<Location> getLocationsForHunt(Connection c, Location obj) throws Exception
	{
		LoggerUtil.logToOut("LocationManager.getLocationsForHunt("+obj.getHuntId()+")");
		return executeSql(c, GET_FOR_HUNT_QRY, GET_FOR_HUNT, obj);
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Location obj) throws Exception
	{
		executeSql(c, DELETE_QRY, DELETE, obj);
	}
	
	
}
