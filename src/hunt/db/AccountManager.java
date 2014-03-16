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

import hunt.beans.Account;

public class AccountManager 
{

	// variables
	private int id;
	
	// actions
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String GET = "GET";
	private String LOGIN = "LOGIN";
	
	// queries
	private String INSERT_QRY = "INSERT INTO accounts (first_name, last_name, email, phone_number, username, password) values (?,?,?,?,?,?)";
	private String UPDATE_QRY = "UPDATE accounts SET first_name = ?, last_name = ?, email = ?, phone_number = ?, username = ?, password = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM accounts WHERE id = ?";
	private String GET_QRY = "SELECT * FROM accounts WHERE id = ?";
	private String LOGIN_QRY = "SELECT * FROM accounts WHERE username = ? AND password = ?";
	
	/**
	 * process result set in one place
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Vector<Account> process(ResultSet rs) throws Exception
	{
		Vector<Account> objs = new Vector<Account>();
		while (rs.next())
		{
			objs.add(new Account(rs.getString("id"), 
					          rs.getString("first_name"), 
					          rs.getString("last_name"), 
					          rs.getString("email"),
					          rs.getString("phone_number"),
					          rs.getString("username"),
					          rs.getString("password")));
		}
		return objs;
	}
	
	/**
	 * match queries with actions and execute in one place
	 * @param c
	 * @param sql
	 * @param action
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private Vector<Account> executeSql(Connection c, String sql, String action, Account obj) throws Exception
	{
		// instantiate list object
		Vector<Account> objs = new Vector<Account>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setString(1, obj.getFirstName());
			pst.setString(2, obj.getLastName());
			pst.setString(3, obj.getEmail());
			pst.setString(4, obj.getPhone());
			pst.setString(5, obj.getUsername());
			pst.setString(6, obj.getPassword());
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setString(1, obj.getFirstName());
			pst.setString(2, obj.getLastName());
			pst.setString(3, obj.getEmail());
			pst.setString(4, obj.getPhone());
			pst.setString(5, obj.getUsername());
			pst.setString(6, obj.getPassword());
			pst.setInt(7, Integer.parseInt(obj.getId()));
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
		else if (action.equals(LOGIN))
		{
			pst.setString(1, obj.getUsername());
			pst.setString(2, obj.getPassword());
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}		
		
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Account>();
		
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
	public int insert(Connection c, Account obj) throws Exception
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
	public void update(Connection c, Account obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Account obj) throws Exception
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
	public Account get(Connection c, Account obj) throws Exception
	{
		Account a = new Account();
		
		Vector <Account> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null && objs.size() > 0)
			a = objs.get(0);
		
		return a;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Account login(Connection c, Account obj) throws Exception
	{
		Account a = new Account();
		
		Vector <Account> objs = executeSql(c, LOGIN_QRY, LOGIN, obj);
		
		if (objs != null)
			a = objs.get(0);
		
		return a;
	}	
	
}
