package hunt.web;

import hunt.beans.Location;
import hunt.business.HuntCommand;
import hunt.business.LocationCommand;
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
public class LocationGateway extends HttpServlet {

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
		String url = "/editlocation.jsp";
		
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
			
			LoggerUtil.logToOut("LocationGateway - action: " + action);
			
			LocationCommand cmd = new LocationCommand();
			
			if (action.equals("createlocation") || action.equals("editlocation"))
			{
				String locationId = req.getParameter("locationid");
				if (locationId == null)
					locationId = "0";
				
				String huntId = req.getParameter("huntid");
				if (huntId == null)
					huntId = "0";	
				
				String name = req.getParameter("name");
				if (name == null)
					name = "";	
				
				String code = req.getParameter("code");
				if (code == null)
					code = "";	
				
				String key = req.getParameter("key");
				if (key == null)
					key = "";	
				
				String address = req.getParameter("address");
				if (address == null)
					address = "";			
				
				String phone = req.getParameter("phone");
				if (phone == null)
					phone = "0";		

				String specialLocationId = req.getParameter("speciallocationid");
				if (specialLocationId == null || specialLocationId.equals(""))
					specialLocationId = "0";		
				
				String hasSpecial = req.getParameter("hasspecial");
				if (hasSpecial == null)
					hasSpecial = "N";		
				
				if (name.equals("") || 
					key.equals("") || 
					code.equals(""))
				{
					eMsg = "Please enter all required fields";
				}
				else
				{
					// get connection and put one in the session
					Connection con = (Connection) session.getAttribute("CONNECTION");

					LoggerUtil.logToOut("values: " + locationId + ", " + huntId + ", " + name + ", " + code + ", " + key + ", " + address + ", " + phone + 
							specialLocationId + ", " + hasSpecial);
					
					Location location = new Location();
					
					if (action.equals("editlocation"))
					{
						location = cmd.updateLocation(con, locationId, huntId, name, code, key, address, phone, specialLocationId, hasSpecial);
						msg = "Location updated successfully.";
					}
					else if (action.equals("createlocation"))
					{
						location = cmd.insertLocation(con, huntId, name, code, key, address, phone, specialLocationId, hasSpecial);
						msg = "Location created successfully";
					}
					
					if (location.getId() == null || location.getId().equals("") || location.getId().equals(""))
					{
						eMsg = "Location not created";
						url = "/editlocation.jsp";
					}
					
					session.removeAttribute("LOCATION");
					session.setAttribute("LOCATION", location);
				}
			}
			else if (action.equals("loadlocation"))
			{
				session.removeAttribute("LOCATION");
				session.setAttribute("LOCATION", cmd.getLocationAndQuestions((Connection)session.getAttribute("CONNECTION"), req.getParameter("locationid")));
				url = "/editlocation.jsp";
			}
			else if (action.equals("deletelocation"))
			{
				cmd.deleteLocation((Connection)session.getAttribute("CONNECTION"), Integer.parseInt(req.getParameter("locationid")));
				session.removeAttribute("HUNT");
				session.setAttribute("HUNT", new HuntCommand().getHuntWithLocationsAndTeams((Connection)session.getAttribute("CONNECTION"), req.getParameter("huntid")));
				url = "/edithunt.jsp";
			}
			else if (action.equals("createnewlocation"))
			{
				session.removeAttribute("LOCATION");
				Location location = new Location("0", req.getParameter("huntid"), "", "", "", "", "", "", "");
				session.setAttribute("LOCATION", location);
				url = "/editlocation.jsp";
			}
		}
		catch (Exception e)
		{
			eMsg = "Exception.  message: " + e.getMessage();
			e.printStackTrace();
			url = "/editlocation.jsp";
		}
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
