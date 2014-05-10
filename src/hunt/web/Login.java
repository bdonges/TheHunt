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
public class Login extends HttpServlet {

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
		String url = "/index.jsp";
		
		// set up message variables
		String eMsg = "";
		String msg = "";
		
		// get session
		HttpSession session = req.getSession();
		
		session.removeAttribute("EMSG");
		session.removeAttribute("MSG");
		
		try
		{
		
			String username = req.getParameter("username");
			if (username == null)
				username = "";
			
			String password = req.getParameter("password");
			if (password == null)
				password = "";
			
			LoggerUtil.logToOut("logging into TheHunt: " + username + ", " + password);
			
			if (!username.equals("") && !password.equals(""))
			{
				AccountCommand aCmd = new AccountCommand();
				
				// get connection and put one in the session
				Connection con = (Connection) session.getAttribute("CONNECTION");
				if (con == null)
					con = aCmd.getMySqlConnection();

				// try login
				Account account = aCmd.login(con, username, password);
				
				if (account.getId() == null || account.getId().equals("") || account.getId().equals(""))
				{
					eMsg = "Account not found";
				}
				else
				{
					// set account in session
					account = aCmd.getAccountWithHunts(con, Integer.parseInt(account.getId()));
					session.removeAttribute("ACCOUNT");
					session.setAttribute("ACCOUNT", account);
					
					// set connection in session
					session.setAttribute("CONNECTION", con);
					
					eMsg = "";
					msg = "";
				}
			}
			else
			{
				eMsg = "Please enter a username and password";
				url = "/login.jsp";
			}
		
		}
		catch (Exception e)
		{
			eMsg = "please enter a username and password";
			url = "/login.jsp";
		}
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
