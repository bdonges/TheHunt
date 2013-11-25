package hunt.beans;

import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;

public class Account extends BeanUtils
{

	/**
	 * empty constructor
	 */
	public Account()
	{
	}
	
	/**
	 * populates an Account object from a BasicDBObject  
	 */
	public Account(BasicDBObject obj) 
	{
		convertDBObjectToAccount(obj);
	}	
	
	/**
	 * populates an Account object from data passed
	 * @param id
	 */
	public Account(String id, String firstName, String lastName, String email, String phone)
	{
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPhone(phone);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "firstName","lastName","email","phone"};
	private Hashtable<String,Object> data;
	private String json;
	private Vector<Hunt> hunts = new Vector<Hunt>();
	
	// --------------------------------------------------------------------------------------
	// other methods
    /**
	 * converts an Account object into a BasicDBObject (mongodb)
	 * @return BasicDBObject
	 */
	public BasicDBObject convertAccountToBasicDBObject()
	{
		BasicDBObject obj = convertObjectToBasicDBObject(data);
		setJson(obj.toString());
		return obj;
	}
	
	/**
	 * converts a BasicDBObject (mongodb) into an Account
	 * @param obj
	 */
	public void convertDBObjectToAccount(BasicDBObject obj)
	{
		setJson(obj.toString());
		data = convertBasicDBObjectToObject(obj, cols);
	}		
	
	/**
	 * generates string for log entry
	 * @return
	 */
	public String getLogEntry()	
	{ 
		return generateLogEntry(data, "Account"); 
	}
	
	/**
	 * prints log entry to sysout
	 */
	public void show() 
	{ 
		printToOut(generateLogEntry(data, "Account")); 
	}
	
	// --------------------------------------------------------------------------------------
	// getters and setters
	public String getJson() { return this.json; }
	public String getId() { return data.get("id").toString(); }
	public String getFirstName() { return data.get("firstName").toString(); }
	public String getLastName() { return data.get("lastName").toString(); }
	public String getEmail() { return data.get("email").toString(); }
	public String getPhone() { return data.get("phone").toString(); }
	public Vector<Hunt> getHunts() { return this.hunts; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setFirstName(String firstName) { data.put("firstName",firstName); }
	public void setLastName(String lastName) { data.put("lastName",lastName); }
	public void setEmail(String email) { data.put("email",email); }
	public void setPhone(String phone) { data.put("phone",phone); }
	public void setHunts(Vector<Hunt> hunts) { this.hunts = hunts; }
}
