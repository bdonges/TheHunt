package hunt.business;

import java.sql.Connection;
import java.util.Vector;

import hunt.beans.Account;
import hunt.beans.Hunt;
import hunt.db.HuntManager;

public class HuntCommand extends Command
{

	HuntManager mgr = new HuntManager();
	
	public Hunt getHuntStatus(Connection con, String id) throws Exception 
	{
		Hunt h = mgr.get(con, new Hunt(id, "", "", ""));
		
		h.setTeams(new TeamCommand().getAllTeamResultsForHunt(con, id));
		h.setLocations(new LocationCommand().getLocationsForHunt(con, id));
		
		return h;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Hunt> getHuntsForAccount(Connection con, int accountId) throws Exception
	{
		Vector<Hunt> hunts = new Vector<Hunt>();
		hunts = mgr.getForAccount(con, new Hunt("", String.valueOf(accountId), "", ""));

		return hunts;
	}
	
	/**
	 * 
	 * @param huntId
	 * @return
	 * @throws Exception
	 */
	public Hunt getHunt(Connection con, String huntId) throws Exception
	{
		return mgr.get(con, new Hunt(huntId, "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param accountId
	 * @param name
	 * @param runDate
	 * @throws Exception
	 */
	public Hunt insertHunt(Connection con, int accountId, String name, String runDate) throws Exception
	{
		int id = mgr.insert(con, new Hunt("", String.valueOf(accountId), name, runDate));
		return mgr.get(con, new Hunt(String.valueOf(id), "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @param accountId
	 * @param name
	 * @param runDate
	 * @return
	 * @throws Exception
	 */
	public Hunt updateHunt(Connection con, int huntId, int accountId, String name, String runDate) throws Exception 
	{
		mgr.update(con, new Hunt(String.valueOf(huntId), String.valueOf(accountId), name, runDate));
		return mgr.get(con, new Hunt(String.valueOf(huntId), "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @throws Exception
	 */
	public void deleteHunt(Connection con, int huntId) throws Exception 
	{
		mgr.delete(con, new Hunt(String.valueOf(huntId), "", "", ""));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception
	{
		System.out.println("start test");
	
		// get connection through command
		System.out.println("   getting connection...");
		Connection c = getMySqlConnection();

		// create account
		System.out.println("   create account...");
		Account a = new AccountCommand().insertAccount(c, "test", "account", "testaccount@m.com", "1112223333", "test", "test");
		
		// set up object 
		System.out.println("   setting up hunt...");
		Hunt h1 = new Hunt("", a.getId(), "test_hunt", "2/1/2014 12:00:00");
		h1.show();
		
		// insert hunt
		System.out.println("   inserting hunt...");
		int id = mgr.insert(c, h1);
		h1.setId(String.valueOf(id));
		
		// get hunt
		System.out.println("   get hunt...");
		Hunt h2 = mgr.get(c, h1);
		h2.show();
		
		// update hunt
		System.out.println("   update hunt...");
		h2.setName("TEST_HUNT_U");
		h2.setRunDate("3/1/2014 9:00:00");
		mgr.update(c, h2);
		
		// get hunt
		System.out.println("   get hunt...");
		Hunt h3 = mgr.get(c, h2);
		h3.show();
		
		// delete hunt
		System.out.println("   delete hunt...");
		mgr.delete(c, h3);
		
		// delete account
		System.out.println("   delete account...");
		new AccountCommand().deleteAccount(c, Integer.parseInt(a.getId()));
		
		// closing connection
		System.out.println("   closing connection...");
		c.close();
		
		System.out.println("end test");		
	}
}
