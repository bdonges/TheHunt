package hunt.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlFactory 
{


	public static void main(String[] args)
	{
		try 
		{
			String dbIp   = "127.0.0.1";
			String dbPort = "3307";
			String dbName = "hunt";
			String dbUser = "root";
			String dbPass = "eag1es";			
			
			System.out.println("starting MySqlManager test");
			MySqlFactory m = new MySqlFactory(); 
			System.out.println("getting connection...");
			Connection c = m.getConnection(dbIp, dbPort, dbName, dbUser, dbPass); 
			System.out.println("is connection closed? " + c.isClosed());
			System.out.println("closing connection...");
			m.closeConnection(c);
			System.out.println("is connection closed? " + c.isClosed());
			System.out.println("ending MySqlManager test");
		}
		catch (Exception e)
		{
			System.err.println("exception - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(String dbIp, String dbPort, String dbName, String dbUser, String dbPass) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://"+dbIp+":"+dbPort+"/"+dbName, dbUser, dbPass);
		return c;
	}
	
	public void closeConnection(Connection c) throws Exception
	{
		c.close();
		c = null;
	}
	
}
