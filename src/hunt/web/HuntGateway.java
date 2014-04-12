package hunt.web;

import hunt.beans.Account;
import hunt.beans.Hunt;
import hunt.business.AccountCommand;
import hunt.business.HuntCommand;
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
public class HuntGateway extends HttpServlet {

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
		String url = "/edithunt.jsp";
		
		// set up message variables
		String eMsg = "";
		String msg = "";
		
		// get session
		HttpSession session = req.getSession();
		
		try
		{
			HuntCommand cmd = new HuntCommand();
			
			String action = req.getParameter("a");
			if (action == null)
				action = "";
			
			LoggerUtil.logToOut("HuntGateway - action: " + action);
			
			if (action.equals("createhunt") || action.equals("edithunt"))
			{
				String huntId = req.getParameter("huntid");
				if (huntId == null)
					huntId = "0";
				
				String accountId = req.getParameter("accountid");
				if (accountId == null)
					accountId = "0";
				
				String name = req.getParameter("name");
				if (name == null)
					name = "";
				
				String runDate = req.getParameter("rundate");
				if (runDate == null)
					runDate = "";
				
				LoggerUtil.logToOut("action: " + action);
				LoggerUtil.logToOut("values: " + huntId + ", " + accountId+ ", " + name + ", " + runDate);
				
				if (name.equals("") || runDate.equals(""))
				{
					eMsg = "Please enter the required fields";
				}
				else
				{
					Hunt hunt = new Hunt();

					if (action.equals("createhunt"))
					{
						hunt = cmd.insertHunt((Connection)session.getAttribute("CONNECTION"), Integer.parseInt(accountId), name, runDate);
						
						hunt = cmd.getHuntWithLocationsAndTeams((Connection)session.getAttribute("CONNECTION"), hunt.getId());
						msg = "Hunt created successfully";
					}
					else if (action.equals("edithunt"))
					{
						hunt = cmd.updateHunt((Connection)session.getAttribute("CONNECTION"), Integer.parseInt(huntId), Integer.parseInt(accountId), name, runDate);
						hunt = cmd.getHuntWithLocationsAndTeams((Connection)session.getAttribute("CONNECTION"), hunt.getId());
						msg = "Hunt updated successfully";
					}
					
					session.removeAttribute("HUNT");
					session.setAttribute("HUNT", hunt);
				}
			}
			else if (action.equals("createnewhunt"))
			{
				session.setAttribute("HUNT", new Hunt("0", req.getParameter("accountid"), "", ""));
			}
			else if (action.equals("starthunt"))
			{
				
			}
			else if (action.equals("deletehunt"))
			{
				cmd.deleteHunt((Connection)session.getAttribute("CONNECTION"), Integer.parseInt(req.getParameter("huntid")));
				Account account = new AccountCommand().getAccountWithHunts((Connection)session.getAttribute("CONNECTION"), 
						Integer.parseInt(req.getParameter("accountid")));
				session.removeAttribute("ACCOUNT");
				session.setAttribute("ACCOUNT", account);
				url = "/index.jsp";
			}		
			else if (action.equals("loadhunt"))
			{
				session.removeAttribute("HUNT");
				session.setAttribute("HUNT", cmd.getHuntWithLocationsAndTeams((Connection)session.getAttribute("CONNECTION"), req.getParameter("huntid")));
			}
		}
		catch (Exception e)
		{
			eMsg = "Exception.  message: " + e.getMessage();
			e.printStackTrace();
		}
		
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}

