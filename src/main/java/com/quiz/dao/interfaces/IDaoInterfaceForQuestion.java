package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.Question;

public interface IDaoInterfaceForQuestion {

	/*
	 		private Integer questionid;
			private Quiz quizid;
			private String questionstring;
	  	
	 */
	
	
	public void save(Question question);
	public void update(Question question);
	public void delete(Question question);
	
	public Question getQuestionById(Integer questionid);
	public List<Question> getAllQuestionsForQuiz(Integer quizid);
		
}

