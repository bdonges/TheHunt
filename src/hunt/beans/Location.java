package hunt.beans;

import hunt.utils.LoggerUtil;

import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Location extends BeanUtils 
{
	/**
	 * empty constructor
	 */
	public Location()
	{
	}
	
	/**
	 * populates a Location object from a BasicDBObject  
	 */
	public Location(DBObject obj) 
	{
		convertDBObjectToLocation(obj);
	}	
	
	/**
	 * populates an Location object from data passed
	 * @param id
	 */
	public Location(String id, String huntId, String name, String code, String key, String address, 
			String phoneNumber, String specialLocationId, String hasSpecial)
	{
		this.setId(id);
		this.setHuntId(huntId);
		LoggerUtil.logToOut("LocationConstructor... huntId passed: " + huntId + ", huntId set: " + this.getHuntId());
		this.setName(name);
		this.setCode(code);
		this.setKey(key);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
		this.setSpecialLocationId(specialLocationId);
		this.setHasSpecial(hasSpecial);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "huntId", "name", "code", "key", "address", "phoneNumber", "specialLocationId", "hasSpecial"};
	private Hashtable<String,Object> data = new Hashtable<String,Object>();
	private String json;
	
	private Vector<Question> questions = new Vector<Question>();
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts a Location object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertLocationToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into a Location
	 * @param obj
	 */
	public void convertDBObjectToLocation(DBObject obj)
	{
		setJson(obj.toString());
		data = convertDBObjectToObject(obj, cols);
	}		
	
	/**
	 * generates string for log entry
	 * @return
	 */
	public String getLogEntry()	
	{ 
		return generateLogEntry(data, "Location"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Location")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getHuntId() { return data.get("huntId").toString(); }
	public String getName() { return data.get("name").toString(); }
	public String getCode() { return data.get("code").toString(); }
	public String getKey() { return data.get("key").toString(); }
	public String getAddress() { return data.get("address").toString(); }
	public String getPhoneNumber() { return data.get("phoneNumber").toString(); }
	public String getSpecialLocationId() { return data.get("specialLocationId").toString(); }
	public String getHasSpecial() { return data.get("hasSpecial").toString(); }
	public Vector<Question> getQuestions() { return this.questions; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setHuntId(String huntId) { data.put("huntId",huntId); }
	public void setName(String name) { data.put("name",name); }
	public void setCode(String code) { data.put("huntId",code); }
	public void setKey(String key) { data.put("key",key); }
	public void setAddress(String address) { data.put("address",address); }
	public void setPhoneNumber(String phoneNumber) { data.put("phoneNumber",phoneNumber); }
	public void setSpecialLocationId(String specialLocationId) { data.put("specialLocationId",specialLocationId); }
	public void setHasSpecial(String hasSpecial) { data.put("hasSpecial",hasSpecial); }
	public void setQuestions(Vector<Question> questions) { this.questions = questions; }

}
