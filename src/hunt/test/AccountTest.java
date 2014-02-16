package hunt.test;

import java.util.ArrayList;

import com.mongodb.DB;


import hunt.beans.Account;
import hunt.business.AccountManager;
import hunt.db.MongoFactory;

public class AccountTest extends TestUtils
{

	private static final String COLLECTION_NAME = "accounts";
	
	public static void main(String[] args)
	{
		try
		{	
			System.out.println("starting...");
			MongoFactory mf = new MongoFactory();
			DB db = mf.getDatabase("192.168.0.105", "hunt", 27017);
			
			Account a = new Account();
			a.setFirstName("anna");
			a.setLastName("donges");
			a.setEmail("anna@yahoo.com");
			a.setPhone("6787930698");
			
			AccountManager aMgr = new AccountManager();
			
			System.out.println("1.  get accounts");
			ArrayList <Account> accounts = aMgr.getAll(db);
			for (Account account : accounts)
			{
				System.out.println(account.getJson());
			}
			
			System.out.println("2.  upsert account");
			aMgr.upsert(a, db);
			
			System.out.println("3.  get accounts (again)");
			accounts.clear();
			accounts = aMgr.getAll(db);
			for (Account account : accounts)
			{
				System.out.println(account.getJson());
			}

			System.out.println("4.  get one account (5293f0e23144f2eee4e58942)");
			Account account = aMgr.findOne("5293f0e23144f2eee4e58942", db);
			System.out.println("account id: " + account.getId() + ", " + account.getEmail());
			
			System.out.println("done");
		}
		catch (Exception e)
		{
			System.err.println("error - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
