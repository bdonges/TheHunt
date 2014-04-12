package hunt.business;

import java.sql.Connection;
import java.util.Vector;

import hunt.beans.Account;
import hunt.beans.Hunt;
import hunt.beans.Location;
import hunt.db.LocationManager;
import hunt.utils.LoggerUtil;

public class LocationCommand extends Command
{

	private LocationManager mgr = new LocationManager();
	
	public LocationCommand()
	{
	}

	/**
	 * 
	 * @param con
	 * @param id
	 * @param huntId
	 * @param name
	 * @param code
	 * @param key
	 * @param address
	 * @param phone
	 * @param specialLocationId
	 * @param hasSpecial
	 * @return
	 * @throws Exception
	 */
	public Location updateLocation(Connection con, String id, String huntId, String name, String code, String key, 
			String address, String phone, String specialLocationId, String hasSpecial) throws Exception
	{
		mgr.update(con, new Location(id, huntId, name, code, key, address, phone, specialLocationId, hasSpecial));
		return mgr.get(con, new Location(id, "", "", "", "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @param name
	 * @param code
	 * @param key
	 * @param address
	 * @param phone
	 * @param specialLocationId
	 * @param hasSpecial
	 * @return
	 * @throws Exception
	 */
	public Location insertLocation(Connection con, String huntId, String name, String code, String key, 
			String address, String phone, String specialLocationId, String hasSpecial) throws Exception
	{
		int id = mgr.insert(con, new Location("", huntId, name, code, key, address, phone, specialLocationId, hasSpecial));
		Location l = mgr.get(con, new Location(String.valueOf(id), "", "", "", "", "", "", "", ""));
		return l;
	}	

	/**
	 * 
	 * @param con
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	public Location getLocation(Connection con, String id) throws Exception
	{
		return mgr.get(con, new Location(id, "", "", "", "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Location getLocationAndQuestions(Connection con, String id) throws Exception
	{
		Location l =  mgr.get(con, new Location(id, "", "", "", "", "", "", "", ""));
		l.setQuestions(new QuestionCommand().getQuestionsForLocation(con, id));
		return l;
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @return
	 * @throws Exception
	 */
	public Vector<Location> getLocationsForHunt(Connection con, String huntId) throws Exception
	{
		LoggerUtil.logToOut("LocationCommand.getLocationsForHunt("+huntId+")");
		Location location = new Location();
		location.setHuntId(huntId);
		LoggerUtil.logToOut("LocationCommand.getLocationsForHunt() - huntId from location object: " + location.getHuntId());
		return mgr.getLocationsForHunt(con, location);
	}
	
	/**
	 * 
	 * @param con
	 * @param huntId
	 * @return
	 * @throws Exception
	 */
	public Vector<Location> getLocationsWithQuestionsForHunt(Connection con, String huntId) throws Exception
	{
		Vector<Location> locations = new Vector<Location>();
		QuestionCommand qcmd = new QuestionCommand();
		Vector<Location> v = mgr.getLocationsForHunt(con, new Location("", huntId, "", "", "", "", "", "", ""));
		for (Location l : v)
		{
			l.setQuestions(qcmd.getQuestionsForLocation(con, l.getId()));
			locations.add(l);
		}
		return locations;
	}
	
	/**
	 * 
	 * @param con
	 * @param locationId
	 * @throws Exception
	 */
	public void deleteLocation(Connection con, int locationId) throws Exception
	{
		mgr.delete(con, new Location(String.valueOf(locationId), "", "", "", "", "", "", "", ""));
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
		
		// create hunt 
		System.out.println("   create hunt...");
		Hunt h = new HuntCommand().insertHunt(c, Integer.parseInt(a.getId()), "test_hunt", "2/1/2014 12:00:00");
		
		// create location
		System.out.println("   setting up object...");
		Location l1 = new Location("", h.getId(), "name", "code", "key", "address", "1112223333", "", "N");
		l1.show();
		
		// insert location
		System.out.println("   inserting location...");
		int id = mgr.insert(c, l1);
		l1.setId(String.valueOf(id));
		
		// get location
		System.out.println("   getting location...");
		Location l2 = mgr.get(c, l1);
		l2.show();
		
		// adding questions to location
		System.out.println("   adding questions to location...");
		new QuestionCommand().insertQuestion(c, h.getId(), l2.getId(), "what is your favorite color?", "orange and blue", "5", "1");
		new QuestionCommand().insertQuestion(c, h.getId(), l2.getId(), "what is your favorite fruit?", "apple", "4", "2");
		
		// get location
		System.out.println("   getting location and questions...");
		l2 = getLocationAndQuestions(c, l2.getId());
		l2.show();
		System.out.println("    number of questions?  " + l2.getQuestions().size());
		
		// update location
		System.out.println("   updating location...");		
		l2.setName("NAME_U");
		l2.setCode("CODE_U");
		l2.setKey("KEY_U");
		l2.setAddress("ADDRESS_U");
		l2.setPhoneNumber("3332221111");
		mgr.update(c, l2);
		
		// get location
		System.out.println("   getting location...");
		Location l3 = mgr.get(c, l2);
		l3.show();
		
		// get locations for hunt
		System.out.println("   getting location for hunt...");
		Vector<Location> v1 = getLocationsForHunt(c, h.getId());
		System.out.println("   number of locations for hunt " + h.getId() + " is " + v1.size());
		
		// get locations with questions for hunt
		System.out.println("   get locations with questions for hunt...");
		Vector<Location> v2 = getLocationsWithQuestionsForHunt(c, h.getId());
		System.out.println("   number of locations for hunt " + h.getId() + " is " + v2.size());
		
		// delete location
		System.out.println("   deleting location...");
		mgr.delete(c, l3);
		
		// delete hunt
		System.out.println("   delete hunt...");
		new HuntCommand().deleteHunt(c, Integer.parseInt(h.getId()));
		
		// delete account
		System.out.println("   delete account...");
		new AccountCommand().deleteAccount(c, Integer.parseInt(a.getId()));
		
		// closing connection
		System.out.println("   closing connection...");
		c.close();
		
		System.out.println("end test");			
	}	
	
}
