package hunt.beans;

import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Account extends BeanUtils
{

	/**
	 * empty constructor
	 */
	public Account()
	{
		if (data == null)
			data = new Hashtable<String, Object>();
	}
	
	/**
	 * populates an Account object from a BasicDBObject  
	 */
	public Account(DBObject obj) 
	{
		if (data == null)
			data = new Hashtable<String, Object>();
		convertDBObjectToAccount(obj);
	}	
	
	/**
	 * populates an Account object from data passed
	 * @param id
	 */
	public Account(String id, String firstName, String lastName, String email, String phone, String username, String password)
	{
		if (data == null)
			data = new Hashtable<String, Object>();
		
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPhone(phone);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	// --------------------------------------------------------------------------------------	
	// variables
	private String[] cols = {"id", "firstName","lastName","email","phone", "username", "password"};
	private Hashtable<String,Object> data = new Hashtable<String,Object>();
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
	public void convertDBObjectToAccount(DBObject obj)
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
	public String getId() 
	{ 
		String id = "0";
		try { id = data.get("id").toString(); } catch (NullPointerException e) {}
		return id;
	}
	public String getJson() { return this.json; }
	public String getFirstName() { return data.get("firstName").toString(); }
	public String getLastName() { return data.get("lastName").toString(); }
	public String getEmail() { return data.get("email").toString(); }
	public String getPhone() { return data.get("phone").toString(); }
	public String getUsername() { return data.get("username").toString(); }
	public String getPassword() { return data.get("password").toString(); }
	public Vector<Hunt> getHunts() { return this.hunts; }

	public void setJson(String json) { this.json = json; }
	public void setId(String id) { data.put("id", id); }
	public void setFirstName(String firstName) { data.put("firstName",firstName); }
	public void setLastName(String lastName) { data.put("lastName",lastName); }
	public void setEmail(String email) { data.put("email",email); }
	public void setPhone(String phone) { data.put("phone",phone); }
	public void setUsername(String username) { data.put("username",username); }
	public void setPassword(String password) { data.put("password",password); }	
	public void setHunts(Vector<Hunt> hunts) { this.hunts = hunts; }
}
