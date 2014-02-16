package hunt.beans;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BeanUtils 
{

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String checkNullString(String s)
	{
		if (s == null) 
		{ 
			s = ""; 
		}
		return s;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Vector checkNullVector(Vector v)
	{
		if (v == null)
		{
			v = new Vector();
		}
		return v;
	}

	/**
	 * 
	 * @param s
	 */
	public void printToOut(String s)
	{
		System.out.println(new java.util.Date() + " :: " + s);
	}
	
	/**
	 * 
	 * @param h
	 * @param objectName
	 * @return
	 */
	public String generateLogEntry(Hashtable<String,Object> h, String objectName)
	{
		Enumeration<String> e = h.keys();
		StringBuffer sb = new StringBuffer();
		sb.append("--\n");
		sb.append("  " + objectName);
		while (e.hasMoreElements())
		{
			String k = e.nextElement();
			Object o = (Object)h.get(k);
			sb.append("    key: " + k + ", class: " + o.getClass() + ", value: " + o + "\n");
		}
		sb.append("--");
		return sb.toString();
	}
	
	/**
	 * 
	 * @param obj
	 * @param cols
	 * @return
	 */
	public Hashtable<String,Object> convertDBObjectToObject(DBObject obj, String[] cols)
	{
		Hashtable<String,Object> h = new Hashtable<String,Object>();
		for (String key : cols)
		{
			String val = (String)obj.get(key);
			
			if (val == null)
				val = "";
			
			h.put(key, val);
		}
		return h;
	}
	
	/**
	 * 
	 * @param h
	 * @return
	 */
	public BasicDBObject convertObjectToBasicDBObject(Hashtable<String,Object> h)
	{
		BasicDBObject obj = new BasicDBObject();
		Enumeration<String> e = h.keys();
		while (e.hasMoreElements())
		{
			String key = e.nextElement();
			obj.put(key, String.valueOf(h.get(key)));
		}
		return obj;
	}
	
}
