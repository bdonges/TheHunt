package hunt.business;

import java.sql.Connection;

import hunt.db.MongoFactory;
import hunt.db.MySqlFactory;

import com.mongodb.DB;

public class Command 
{

	// *************************************************************
	// global variables
	public final String MYSQL_TYPE = "mysql";
	public final String MONGO_TYPE = "mongo";
	private String activeDb = MYSQL_TYPE;  
	
	// *************************************************************
	// mongo information
	private String mongoDbName = "test_hunt_1";
	private String mongoIp = "127.0.0.1";
	private int mongoPort = 27017;

	// *************************************************************
	// my sql information
	private final String dbIp   = "127.0.0.1";
	private final String dbPort = "3307";
	private final String dbName = "hunt";
	private final String dbUser = "root";
	private final String dbPass = "eag1es";

	// *************************************************************
	// getter's and setter's
	public String getActiveDb()
	{
		String db = "";
		if (activeDb.equals(MYSQL_TYPE))
			db = MYSQL_TYPE;
		else if (activeDb.equals(MONGO_TYPE))
			db = MONGO_TYPE;
		return db;
	}
	
	// *************************************************************
	// other methods
	
	/**
	 * get's a DB that's passed to the managers
	 * @return
	 * @throws Exception
	 */
	public DB getDB() throws Exception
	{
		return new MongoFactory().getConnection(mongoIp, mongoPort).getDB(mongoDbName);
	}
	
	/**
	 * get's a Connection that passed to the managers
	 * @return
	 * @throws Exception
	 */
	public Connection getMySqlConnection() throws Exception 
	{
		return new MySqlFactory().getConnection(dbIp, dbPort, dbName, dbUser, dbPass);
	}
	
	
}
