package hunt.business;

import com.mongodb.DB;

import hunt.beans.Location;
import hunt.db.LocationManager;

public class LocationCommand 
{

	public LocationCommand()
	{
		
	}
	
	/**
	 * 
	 * @param id
	 * @param huntId
	 * @param name
	 * @param code
	 * @param key
	 * @param address
	 * @param phone
	 * @param specialLocationId
	 * @param hasSpecial
	 * @param db
	 * @return
	 */
	public Location upsertLocation(String id, String huntId, String name, String code, String key, 
			String address, String phone, String specialLocationId, String hasSpecial, DB db)
	{
		Location location = new Location(id, huntId, name, code, key, address, phone, specialLocationId, hasSpecial);
		location = new LocationManager().upsert(location, db);
		return location;
	}

	/**
	 * 
	 * @param locationId
	 * @param db
	 * @return
	 */
	public Location getLocation(String locationId, DB db)
	{
		return new LocationManager().findOne(locationId, db);
	}
	
	/**
	 * 
	 * @param locationId
	 * @param db
	 */
	public void deleteLocation(String locationId, DB db)
	{
		new QuestionCommand().deleteQuestionsForLocation(locationId, db);
		new LocationManager().deleteLocation(locationId, db);
	}
	
}
