package hunt.business;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import hunt.beans.Account;

public class AccountManager 
{

	private final String COLLECTION_NAME = "accounts";
	
	/**
	 * 
	 */
	public AccountManager() 
	{
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public DBCollection getCollection(DB db)
	{
		return db.getCollection(COLLECTION_NAME);
	}
	
	/**
	 * 
	 * @param account
	 * @param db
	 * @return
	 */
	public Account upsert(Account account, DB db) 
	{
		DBCollection col = getCollection(db);	
		col.update(new BasicDBObject("_id", account.getId()), 
			    account.convertAccountToBasicDBObject(), true, false);
		return findOne(account.getId(), db);
	}
	
	/**
	 * 
	 * @param _id
	 * @param db
	 * @return
	 */
	public Account findOne(String _id, DB db)
	{
		System.out.println("findOne(" + _id + ")");
		Account account = new Account();
		BasicDBObject query = new BasicDBObject("_id", _id);
		DBCollection col = getCollection(db);
		DBCursor cursor = col.find(query);

		try
		{
			System.out.println("inside try");
			while(cursor.hasNext()) 
			{
				System.out.println("inside while");
				account.convertDBObjectToAccount(cursor.next());
			}
		} 
		finally 
		{
		   cursor.close();
		}
		return account;
	}
	
	/**
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Account> getAll(DB db)
	{
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		DBCollection c = db.getCollection(COLLECTION_NAME);
		DBCursor cursor = c.find();
		
		try 
		{	
		   while(cursor.hasNext()) 
		   {
		       accounts.add(new Account(cursor.next()));
		   }
		} 
		finally 
		{
		   cursor.close();
		}
		return accounts;
	}
	
}
