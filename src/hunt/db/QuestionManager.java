package hunt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import hunt.beans.Question;

public class QuestionManager 
{
	
	private int id;
	
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String DELETE = "DELETE";
	private String DELETE_FOR_LOCATION = "DELETE_FOR_LOCATION";
	private String GET = "GET";
	private String GET_FOR_LOCATION = "GET_FOR_LOCATION";
	
	private String INSERT_QRY = "INSERT INTO questions (location_id, question, answer, points) values (?,?,?,?)";
	private String UPDATE_QRY = "UPDATE questions SET location_id = ?, question = ?, answer = ?, points = ? WHERE id = ?";
	private String DELETE_QRY = "DELETE FROM questions WHERE id = ?";
	private String DELETE_FOR_LOCATION_QRY = "DELETE FROM questions WHERE location_id = ?";
	private String GET_QRY = "SELECT * FROM questions WHERE id = ?";
	private String GET_FOR_LOCATION_QRY = "SELECT * FROM questions WHERE location_id = ? ORDER BY question_order";
	
	private Vector<Question> process(ResultSet rs) throws Exception
	{
		Vector<Question> objs = new Vector<Question>();
		while (rs.next())
		{
			objs.add(new Question(rs.getString("id"),
								  rs.getString("location_id"),
								  rs.getString("question"),
								  rs.getString("answer"),
								  rs.getString("points"),
								  rs.getString("question_order")));
		}
		return objs;
	}
	
	private Vector<Question> executeSql(Connection c, String sql, String action, Question obj) throws Exception
	{
		// instantiate list object
		Vector<Question> objs = new Vector<Question>();

		// the one prepared statement
		PreparedStatement pst = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		
		// work per action
		if (action.equals(INSERT))
		{
			pst.setInt(1, Integer.parseInt(obj.getLocationId()));
			pst.setString(2, obj.getQuestion());
			pst.setString(3, obj.getAnswer());
			pst.setString(4, obj.getPoints());
			pst.setInt(5, Integer.parseInt(obj.getQuestionOrder()));
			
			id = 0;
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
				id = rs.getInt(1);
			rs.close();
		}
		else if (action.equals(UPDATE))
		{
			pst.setInt(1, Integer.parseInt(obj.getLocationId()));
			pst.setString(2, obj.getQuestion());
			pst.setString(3, obj.getAnswer());
			pst.setString(4, obj.getPoints());
			pst.setInt(5, Integer.parseInt(obj.getQuestionOrder()));
			pst.setInt(6, Integer.parseInt(obj.getId()));
			pst.execute();
		}		
		else if (action.equals(DELETE))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			pst.execute();
		}
		else if (action.equals(DELETE_FOR_LOCATION))
		{
			pst.setInt(1, Integer.parseInt(obj.getLocationId()));
			pst.execute();
		}		
		else if (action.equals(GET))
		{
			pst.setInt(1, Integer.parseInt(obj.getId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		else if (action.equals(GET_FOR_LOCATION))
		{
			pst.setInt(1, Integer.parseInt(obj.getLocationId()));
			ResultSet rs = pst.executeQuery();
			objs = process(rs);
			rs.close();
		}
		
		// close prepared statement
		pst.close();
		
		// ensure we never return a null list
		if (objs == null)
			objs = new Vector<Question>();
		
		return objs;
	}
	
	// *********************************************************************************
	// start public stuff
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public int insert(Connection c, Question obj) throws Exception
	{
		executeSql(c, INSERT_QRY, INSERT, obj);
		return id;
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void update(Connection c, Question obj) throws Exception
	{
		executeSql(c, UPDATE_QRY, UPDATE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Question get(Connection c, Question obj) throws Exception
	{
		Question q = new Question();
		
		Vector <Question> objs = executeSql(c, GET_QRY, GET, obj);
		
		if (objs != null)
			q = objs.get(0);
		
		return q;
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Vector<Question> getQuestionsForLocation(Connection c, Question obj) throws Exception
	{
		return executeSql(c, GET_FOR_LOCATION_QRY, GET_FOR_LOCATION, obj);
	}

	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void delete(Connection c, Question obj) throws Exception
	{
		executeSql(c, DELETE_QRY, DELETE, obj);
	}
	
	/**
	 * 
	 * @param c
	 * @param obj
	 * @throws Exception
	 */
	public void deleteForLocation(Connection c, Question obj) throws Exception
	{
		executeSql(c, DELETE_FOR_LOCATION_QRY, DELETE_FOR_LOCATION, obj);
	}	
		
}
