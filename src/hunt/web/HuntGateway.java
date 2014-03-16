package hunt.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		/*
		 *  create and modify attributes of a hunt
		 *  
		 *  actions:
		 *  
		 *  ch - create hunt
		 *  eh - edit hunt
		 *  dh - delete hunt
		 *  sh - start hunt
		 */
		 
		String action = req.getParameter("a");
		if (action == null)
			action = "";


		
		
		
		
		String url = "/hunt.jsp";
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
