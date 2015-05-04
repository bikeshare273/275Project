package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.entities.QuestionConrrectAnswerRef;

@Transactional
public class QuestionCorrectAnswerDao implements IDaoInterfaceForQuestionCorrectAnswer{

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(QuestionConrrectAnswerRef entity) {

		hibernateTemplate.save(entity);
	}

	@Override
	public void update(QuestionConrrectAnswerRef entity) {

		hibernateTemplate.update(entity);
	}

	@Override
	public void delete(QuestionConrrectAnswerRef entity) {

		hibernateTemplate.delete(entity);
	}

	@Override
	public QuestionConrrectAnswerRef getOptionById(Integer questioncorrectanswerpid) {
		
		String query = "from QuestionConrrectAnswerRef q where q.questioncorrectanswerpid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuestionConrrectAnswerRef> entries = (List<QuestionConrrectAnswerRef>) hibernateTemplate.find(query, questioncorrectanswerpid);
		
		if(entries.isEmpty()) { return null; }
		
		return entries.get(0);
	}

	@Override
	public QuestionConrrectAnswerRef getCorrectOptionForQuestion(Integer questionid) {

		String query = "from QuestionConrrectAnswerRef q where q.questionid = ?";
		
		@SuppressWarnings("unchecked")
		List<QuestionConrrectAnswerRef> entries = (List<QuestionConrrectAnswerRef>) hibernateTemplate.find(query, questionid);
		
		if(entries.isEmpty()) { return null; }
		
		return entries.get(0);

	}

}
