package hunt.business;

import java.sql.Connection;

import hunt.beans.Account;
import hunt.db.AccountManager;

public class AccountCommand extends Command 
{

	private AccountManager mgr = new AccountManager();
	
	/**
	 * 
	 * @param con
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	public Account getAccount(Connection con, int accountId) throws Exception
	{
		return mgr.get(con, new Account(String.valueOf(accountId), "", "", "", "", "", ""));
	}
	
	/**
	 * 
	 * @param con
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Account insertAccount(Connection con, 
								 String firstName, 
								 String lastName, 
								 String email, 
								 String phoneNumber, 
								 String username, 
								 String password) throws Exception
	{
		int id = mgr.insert(con, new Account("", firstName, lastName, email, phoneNumber, username, password));
		return getAccount(con, id);
	}

	/**
	 * 
	 * @param con
	 * @param accountId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public void updateAccount(Connection con,
							  int accountId,
			 				  String firstName, 
			 				  String lastName, 
			 				  String email, 
			 				  String phoneNumber, 
			 				  String username, 
			 				  String password) throws Exception
    {
		mgr.update(con, new Account(String.valueOf(accountId), firstName, lastName, email, phoneNumber, username, password));
    }	
	
	/**
	 * 
	 * @param con
	 * @param accountId
	 * @throws Exception
	 */
	public void deleteAccount(Connection con, int accountId) throws Exception 
	{
		mgr.delete(con, new Account(String.valueOf("accountId"), "", "", "", "", "", ""));
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
		
		// 
		System.out.println("   setting up account");
		Account a1 = new Account("", "test", "account", "testaccount@m.com", "4049958602", "test", "testpassword");
		
		// insert row
		System.out.println("   inserting row...");
		int id = mgr.insert(c, a1);
		a1.setId(String.valueOf(id));
		
		// get row
		System.out.println("   getting row, id: " + id);
		Account a2 = mgr.get(c, a1);
		a2.show();
		
		// login
		System.out.println("   logging in (bdonges,eag1es)...");
		Account loggedIn = mgr.login(c, new Account("", "", "", "", "", "bdonges", "eag1es"));
		loggedIn.show();
		
		// update row
		System.out.println("   updating row...");
		Account a3 = new Account();
		a3.setId(a2.getId());
		a3.setFirstName("TEST_U");
		a3.setLastName("ACCOUNT_U");
		a3.setEmail("TESTACCOUNT_U@M.COM");
		a3.setPhone("2223334444");
		a3.setUsername("TEST_U");
		a3.setPassword("TESTPASSWORD_U");
		a3.show();
		mgr.update(c, a3);
		
		// getting row
		System.out.println("   getting row...");
		Account a4 = mgr.get(c, a3);
		a4.show();
		
		// delete row
		System.out.println("   deleting row...");
		mgr.delete(c, a4);
		
		// getting row
		System.out.println("   getting row...");
		Account a5 = mgr.get(c, a4);
		a5.show();
		
		// closing connection
		System.out.println("   closing connection...");
		c.close();
		
		System.out.println("end test");
	}
}
