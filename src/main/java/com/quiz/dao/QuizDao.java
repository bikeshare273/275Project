package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.entities.Quiz;

@Transactional
public class QuizDao implements IDaoInterfaceForQuiz {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Quiz quiz) {

		hibernateTemplate.save(quiz);
	}

	@Override
	public void update(Quiz quiz) {

		hibernateTemplate.update(quiz);
	}

	@Override
	public void delete(Quiz quiz) {

		hibernateTemplate.delete(quiz);
	}

	@Override
	public Quiz getQuizById(Integer quizid) {

		String query = "from Quiz q where q.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, quizid);
		
		if(quizzes.isEmpty()) { return null; }
		
		return quizzes.get(0);	
	}

	@Override
	public List<Quiz> getQuizByName(String quizname) {

		String query = "from Quiz q where lower(q.quizname) Like lower(?)";
		
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, "%"+quizname+"%");
		
		if(quizzes.isEmpty()) { return null; }
		
		return quizzes;	
	}

	@Override
	public List<Quiz> getAllQuizzesByCategory(String category) {

		String query = "from Quiz q where lower(q.category) Like lower(?) ";
		
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, category);
		
		if(quizzes.isEmpty()) { return null; }
		
		return quizzes;	
		
	}

	@Override
	public List<Quiz> getAllQuizzesByCreatorId(Integer creatorid) {

		String query = "from Quiz q where q.quizcreator = ? ";
		
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, creatorid);
		
		if(quizzes.isEmpty()) { return null; }
		
		return quizzes;	
		
	}

	@Override
	public List<Quiz> getAllQuizzesByLevel(String quizlevel) {

		String query = "from Quiz q where lower(q.quizlevel) Like lower(?)";
		
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, quizlevel);
		
		if(quizzes.isEmpty()) { return null; }
		
		return quizzes;	
		
	}

	@Override
	public List<Quiz> getAllQuzzesByCategoryAndLevel(String category, String quizlevel) {

	String query = "from Quiz q where lower(q.category) Like lower(?) and lower(q.quizlevel) Like lower(?)";
	
	@SuppressWarnings("unchecked")
	List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query, category, quizlevel);
	
	if(quizzes.isEmpty()) { return null; }
	
	return quizzes;	
			
			
	}

	@Override
	public List<Quiz> getAllQuizzesByPopularityDesc() {

		String query = "from Quiz order by popularitycount desc";
		@SuppressWarnings("unchecked")
		List<Quiz> quizzes = (List<Quiz>) hibernateTemplate.find(query);

		if (quizzes.isEmpty()) { return null; } 
		else { return quizzes;}
		
	}
}
