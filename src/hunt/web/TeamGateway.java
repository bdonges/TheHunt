package hunt.web;

import hunt.beans.Team;
import hunt.business.TeamCommand;
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
public class TeamGateway extends HttpServlet {

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
		String url = "/editteam.jsp";
		
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
			
			LoggerUtil.logToOut("TeamGateway - action: " + action);
			
			TeamCommand tCmd = new TeamCommand();
			
			if (action.equals("createteam") || action.equals("editteam"))
			{
				String teamId = req.getParameter("teamid");
				if (teamId == null)
					teamId = "0";
				
				String huntId = req.getParameter("huntid");
				if (huntId == null)
					huntId = "0";				
				
				String name = req.getParameter("name");
				if (name == null)
					name = "";
				
				String password = req.getParameter("password");
				if (password == null)
					password = "";				
				
				if (name.equals("") || 
					password.equals("") || 
					huntId.equals(""))
				{
					eMsg = "Please enter all required fields";
				}
				else
				{
					// get connection and put one in the session
					Connection con = (Connection) session.getAttribute("CONNECTION");

					LoggerUtil.logToOut("action: " + action);
					LoggerUtil.logToOut("values: " + teamId + ", " + huntId + ", " + name + ", " + password);
					
					Team team = new Team();
					
					if (action.equals("editteam"))
					{
						Team tmpTeam = tCmd.getTeam(con, teamId);
						
						team = tCmd.updateTeam(con, teamId, huntId, name, tmpTeam.getScore(), password);

						msg = "team updated successfully.";
					}
					else if (action.equals("createaccount"))
					{
						team = tCmd.insertTeam(con, huntId, name, "0", password);
						msg = "Team created successfully";
					}
					
					if (team.getId() == null || team.getId().equals("") || team.getId().equals(""))
					{
						eMsg = "Team not created";
						url = "/editteam.jsp";
					}
					
					session.removeAttribute("TEAM");
					session.setAttribute("TEAM", team);
				}
			}
			else if (action.equals("loadteam"))
			{
				Team team = tCmd.getTeamAndPlayers((Connection)session.getAttribute("CONNECTION"), req.getParameter("teamid"));
				session.removeAttribute("TEAM");
				session.setAttribute("TEAM", team);
				url = "/editteam.jsp";
			}
			else if (action.equals("createnewteam"))
			{
				Team team = new Team();
				team.setHuntId(req.getParameter("huntid"));
				session.removeAttribute("TEAM");
				session.setAttribute("TEAM", team);
				url = "/editteam.jsp";
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
