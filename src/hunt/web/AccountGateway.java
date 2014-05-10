package hunt.web;

import hunt.beans.Account;
import hunt.business.AccountCommand;
import hunt.utils.LoggerUtil;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class AccountGateway extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		service(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		service(req, res);
	}	
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		// set up return url
		String url = "/editaccount.jsp";
		
		// set up message variables
		String eMsg = "";
		String msg = "";
		
		// get session
		HttpSession session = req.getSession();
		
		try
		{
			String action = req.getParameter("a");
			if (action == null)
				action = "";
			
			LoggerUtil.logToOut("AccountGateway - action: " + action);
			
			AccountCommand aCmd = new AccountCommand();
			
			if (action.equals("createaccount") || action.equals("editaccount"))
			{
				url = "/editaccount.jsp";
				
				String username = req.getParameter("username");
				if (username == null)
					username = "";
				
				String password = req.getParameter("password");
				if (password == null)
					password = "";
				
				String firstname = req.getParameter("firstname");
				if (firstname == null)
					firstname = "";
				
				String lastname = req.getParameter("lastname");
				if (lastname == null)
					lastname = "";

				String email = req.getParameter("email");
				if (email == null)
					email = "";
				
				String phone = req.getParameter("phone");
				if (phone == null)
					phone = "";
				
				String accountId = req.getParameter("accountid");
				if (accountId == null)
					accountId = "0";
				
				if (username.equals("") || 
					password.equals("") || 
					firstname.equals("") || 
					lastname.equals("") || 
					email.equals("") || 
					phone.equals(""))
				{
					eMsg = "Please enter all required fields";
					url = "/editaccount.jsp";
				}
				else
				{
					// get connection and put one in the session
					Connection con = (Connection) session.getAttribute("CONNECTION");
					if (con == null)
						con = aCmd.getMySqlConnection();
					session.setAttribute("CONNECTION", con);
					
					LoggerUtil.logToOut("action: " + action);
					LoggerUtil.logToOut("values: " + accountId + ", " + username + ", " + password + ", " + firstname + 
							", " + lastname + ", " + email + ", " + phone);
					
					Account account = new Account();
					if (action.equals("editaccount"))
					{
						account = aCmd.updateAccount(con, Integer.parseInt(accountId), firstname, lastname, email, phone, username, password);
						session.removeAttribute("ACCOUNT");
						session.setAttribute("ACCOUNT", account);
						msg = "Account updated successfully.";
						url = "/index.jsp";
					}
					else if (action.equals("createaccount"))
					{
						account = aCmd.insertAccount(con, firstname, lastname, email, phone, username, password);
						msg = "Account created successfully.  Please login";
						url = "/login.jsp";	
					}
					
					if (account.getId() == null || account.getId().equals("") || account.getId().equals(""))
					{
						eMsg = "Account not created";
						url = "/editaccount.jsp";
					}
				}
			}
			else if (action.equals("loadaccount"))
			{
				url = "/editaccount.jsp";
			}
			else if (action.equals("createnewaccount"))
			{
				session.removeAttribute("ACCOUNT");
				Account account = new Account("0", "", "", "", "", "", "");
				session.setAttribute("ACCOUNT", account);
				url = "/editaccount.jsp";
			}
			else if (action.equals("mainaccount"))
			{
				session.removeAttribute("ACCOUNT");
				session.setAttribute("ACCOUNT", aCmd.getAccountWithHunts((Connection)session.getAttribute("CONNECTION"), 
						Integer.parseInt(req.getParameter("accountid"))));
				url = "/index.jsp";
			}
		}
		catch (Exception e)
		{
			eMsg = "Exception.  message: " + e.getMessage();
			e.printStackTrace();
			url = "/editaccount.jsp";
		}
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
