package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.entities.QuizAttemptTracking;

@Transactional
public class QuizAttemptTrackingDao implements IDaoInterfaceForQuizAttemptTracking{

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(QuizAttemptTracking quizattempt) {

		hibernateTemplate.save(quizattempt);
	}

	@Override
	public void update(QuizAttemptTracking quizattempt) {

		hibernateTemplate.update(quizattempt);
	}

	@Override
	public void delete(QuizAttemptTracking quizattempt) {

		hibernateTemplate.delete(quizattempt);
	}

	@Override
	public QuizAttemptTracking getQuizAttemptById(Integer quizattemptpid) {

		String query = "from QuizAttemptTracking q where q.quizattemptpid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, quizattemptpid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts.get(0);
		
	}

	@Override
	public QuizAttemptTracking getQuizAttemptByUserIdAndQuizId(Integer userid,	Integer quizid) {

		String query = "from QuizAttemptTracking q where q.userid.userid = ? and q.quizid.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, userid, quizid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts.get(0);
	}

	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsForUser(Integer userid) {

		String query = "from QuizAttemptTracking q where q.userid.userid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, userid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts;
	}

	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByQuizId(Integer quizid) {

		String query = "from QuizAttemptTracking q where q.quizid.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, quizid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts;
		
		
	}

	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByCategory(Integer categoryid) {
		
		String query = "from QuizAttemptTracking q where q.categoryid.categoryid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, categoryid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts;
	}

	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsForUserByCategory(Integer userid, Integer categoryid) {
		
		String query = "from QuizAttemptTracking q where q.userid.userid = ? and q.categoryid.categoryid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, userid, categoryid);
		
		if(quizattempts.isEmpty()) { return null; }
		
		return quizattempts;
	}

	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDesc() {

		String query = "from QuizAttemptTracking order by score desc";
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query);

		if (quizattempts.isEmpty()) { return null; } 
		else { return quizattempts;}
			
	}
	
	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForQuiz(Integer quizid) {

		String query = "from QuizAttemptTracking q where q.quizid.quizid = ? order by score desc";
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, quizid);

		if (quizattempts.isEmpty()) { return null; } 
		else { return quizattempts;}
	}
	
	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForQuizWithLimit(Integer quizid, Integer limit) {

		String query = "from QuizAttemptTracking q where q.quizid.quizid = ? order by score desc LIMIT ?";
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, quizid, limit);

		if (quizattempts.isEmpty()) { return null; } 
		else { return quizattempts;}
	}
	
	@Override
	public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForCategory(Integer categoryid) {

		String query = "from QuizAttemptTracking q where q.categoryid.categoryid = ? order by score desc";
		@SuppressWarnings("unchecked")
		List<QuizAttemptTracking> quizattempts = (List<QuizAttemptTracking>) hibernateTemplate.find(query, categoryid);

		if (quizattempts.isEmpty()) { return null; } 
		else { return quizattempts;}
	}
}
