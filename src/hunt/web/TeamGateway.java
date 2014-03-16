package hunt.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// creates and modifies teams and adds people to a team
		
		
		
		String url = "/index.jsp";
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
