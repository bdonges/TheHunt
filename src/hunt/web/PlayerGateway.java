package hunt.web;

import hunt.beans.Player;
import hunt.business.PlayerCommand;
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
public class PlayerGateway extends HttpServlet {

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
		String url = "/editplayer.jsp";
		
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
			
			LoggerUtil.logToOut("PlayerGateway - action: " + action);
			
			PlayerCommand pCmd = new PlayerCommand();
			
			if (action.equals("createplayer") || action.equals("editplayer"))
			{
				String playerId = req.getParameter("playerid");
				if (playerId == null)
					playerId = "0";	
				
				String teamId = req.getParameter("teamid");
				if (teamId == null)
					teamId = "0";
				
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
				
				if (firstname.equals("") || 
					lastname.equals("") || 
					email.equals("") ||
					phone.equals(""))
				{
					eMsg = "Please enter all required fields";
				}
				else
				{
					// get connection and put one in the session
					Connection con = (Connection) session.getAttribute("CONNECTION");

					LoggerUtil.logToOut("action: " + action);
					LoggerUtil.logToOut("values: " + playerId + ", " + teamId + ", " + firstname + ", " + lastname + ", " + email + ", " + phone);
					
					Player player = new Player();
					
					if (action.equals("editplayer"))
					{
						player = pCmd.updatePlayer(con, playerId, teamId, firstname, lastname, email, phone);
						msg = "Player updated successfully.";
					}
					else if (action.equals("createaccount"))
					{
						player = pCmd.addPlayer(con, firstname, lastname, email, phone, teamId);
						msg = "Player created successfully";
					}
					
					if (player.getId() == null || player.getId().equals("") || player.getId().equals(""))
					{
						eMsg = "Player not created";
						url = "/editplayer.jsp";
					}
					
					session.removeAttribute("PLAYER");
					session.setAttribute("player", player);
				}
			}
			else if (action.equals("loadplayer"))
			{
				Player player = pCmd.getPlayer((Connection)session.getAttribute("CONNECTION"), req.getParameter("playerid"));
				session.removeAttribute("PLAYER");
				session.setAttribute("PLAYER", player);
				url = "/editplayer.jsp";
			}
			else if (action.equals("deleteplayer"))
			{
				pCmd.deletePlayer((Connection)session.getAttribute("CONNECTION"), req.getParameter("playerid"));
				session.removeAttribute("TEAM");
				session.setAttribute("TEAM", new TeamCommand().getTeam((Connection)session.getAttribute("CONNECTION"), req.getParameter("teamid")));
				url = "/editteam.jsp";
			}
		}
		catch (Exception e)
		{
			eMsg = "Exception.  message: " + e.getMessage();
			e.printStackTrace();
			url = "/editplayer.jsp";
		}
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
