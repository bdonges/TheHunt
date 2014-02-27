package hunt.business;

import java.util.ArrayList;
import java.util.List;

import hunt.beans.Hunt;
import hunt.beans.Team;
import hunt.beans.TeamLocation;
import hunt.beans.TeamAnswer;
import hunt.db.QuestionManager;
import hunt.db.TeamAnswerManager;
import hunt.db.TeamLocationManager;

import com.mongodb.DB;

public class TeamResultsCommand 
{

	public TeamResultsCommand() 
	{
		
	}
	
	/**
	 * 
	 * @param team
	 * @param hunt
	 * @param db
	 * @return
	 */
	public Team getTeamResultsForHunt(Team team, Hunt hunt, DB db)
	{
		TeamLocationManager tlMgr = new TeamLocationManager();
		
		List<TeamLocation> locs = tlMgr.getAllForTeam(team, db);
		List<TeamLocation> locsWithAnswers = new ArrayList<TeamLocation>();
		if (locs != null && locs.size() > 0)
		{
			for (TeamLocation loc : locs)
			{
				locsWithAnswers.add(getLocationAndAnswers(loc, db));
			}
			team.setTeamLoations(locsWithAnswers);
		}
		else
		{
			team.setTeamLoations(new ArrayList<TeamLocation>());
		}
	
		return team;
	}
	
	/**
	 * 
	 * @param tl
	 * @param db
	 * @return
	 */
	public TeamLocation getLocationAndAnswers(TeamLocation tl, DB db)
	{
		List<TeamAnswer> answers = new TeamAnswerManager().getAllForTeamLocation(tl, db);
		List<TeamAnswer> answersWithQuestions = new ArrayList<TeamAnswer>();
		if (answers != null && answers.size() > 0)
		{
			for (TeamAnswer answer : answers)
			{
				answer = getTeamAnswerWithQuestion(answer, db);
				answersWithQuestions.add(answer);
			}
		}
		
		tl.setTeamAnswers(answersWithQuestions);
		return tl;
	}
	
	/**
	 * 
	 * @param answer
	 * @param db
	 * @return
	 */
	public TeamAnswer getTeamAnswerWithQuestion(TeamAnswer answer, DB db)
	{
		answer.setQuestion(new QuestionManager().findOne(answer.getQuestionId(), db));
		return answer;
	}
}
