package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.entities.Question;

@Transactional
public class QuestionDao implements IDaoInterfaceForQuestion {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Question question) {

		hibernateTemplate.save(question);
	}

	@Override
	public void update(Question question) {

		hibernateTemplate.update(question);
	}

	@Override
	public void delete(Question question) {

		hibernateTemplate.delete(question);
	}

	@Override
	public Question getQuestionById(Integer questionid) {

		String query = "from Question q where q.questionid = ?";
		
		@SuppressWarnings("unchecked")
		List<Question> questions = (List<Question>) hibernateTemplate.find(query, questionid);
		
		if(questions.isEmpty()) { return null; }
		
		return questions.get(0);
	}

	@Override
	public List<Question> getAllQuestionsForQuiz(Integer quizid) {

		String query = "from Question q where q.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<Question> questions = (List<Question>) hibernateTemplate.find(query, quizid);
		
		if(questions.isEmpty()) { return null; }
		
		return questions;
	}	

}
