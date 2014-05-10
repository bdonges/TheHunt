package hunt.web;

import hunt.beans.Question;
import hunt.beans.Team;
import hunt.business.QuestionCommand;
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
public class QuestionGateway extends HttpServlet {

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
		String url = "/editquestion.jsp";
		
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
			
			LoggerUtil.logToOut("QuestionGateway - action: " + action);
			
			QuestionCommand cmd = new QuestionCommand();
			
			if (action.equals("createquestion") || action.equals("editquestion"))
			{
				String questionId = req.getParameter("questionid");
				if (questionId == null)
					questionId = "0";
				
				String locationId = req.getParameter("locationid");
				if (locationId == null)
					locationId = "0";		
				
				String huntId = req.getParameter("huntid");
				if (huntId == null)
					huntId = "0";					
				
				String question = req.getParameter("question");
				if (question == null)
					question = "";
				
				String answer = req.getParameter("answer");
				if (answer == null)
					answer = "";
				
				String points = req.getParameter("points");
				if (points == null)
					points = "0";	
				
				String questionOrder = req.getParameter("questionorder");
				if (questionOrder == null)
					questionOrder = "1";					
				
				if (question.equals("") || answer.equals(""))
				{
					eMsg = "Please enter all required fields";
				}
				else
				{
					// get connection and put one in the session
					Connection con = (Connection) session.getAttribute("CONNECTION");

					LoggerUtil.logToOut("values: " + questionId + ", " + locationId + ", " + huntId + ", " + question + ", " + answer + 
							", " + points + ", " + questionOrder);
					
					Question q = new Question();
					
					if (action.equals("editquestion"))
					{
						q = cmd.updateQuestion(con, questionId, huntId, locationId, question, answer, points, questionOrder);
						msg = "Question updated successfully.";
					}
					else if (action.equals("createquestion"))
					{
						q = cmd.insertQuestion(con, huntId, locationId, question, answer, points, questionOrder);
						msg = "Question created successfully";
					}
					
					if (q.getId() == null || q.getId().equals("") || q.getId().equals(""))
					{
						eMsg = "Question not created";
						url = "/editquestion.jsp";
					}
					
					session.removeAttribute("QUESTION");
					session.setAttribute("QUESTION", q);
				}
			}
			else if (action.equals("loadquestion"))
			{
				Question question = cmd.getQuestion((Connection)session.getAttribute("CONNECTION"), req.getParameter("questionid"));
				session.removeAttribute("QUESTION");
				session.setAttribute("QUESTION", question);
				url = "/editquestion.jsp";
			}
			else if (action.equals("createnewquestion"))
			{
				session.removeAttribute("QUESTION");
				Question question = new Question("0", req.getParameter("locationid"), "", "", "", "");
				session.setAttribute("QUESTION", question);
				url = "/editquestion.jsp";
			}
		}
		catch (Exception e)
		{
			eMsg = "Exception.  message: " + e.getMessage();
			e.printStackTrace();
			url = "/editquestion.jsp";
		}
		
		session.setAttribute("EMSG", eMsg);
		session.setAttribute("MSG", msg);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);		
		rd.forward(req, res);
	}
	
}
