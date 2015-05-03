package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.Question;
import com.quiz.entities.Quiz;

public interface IDaoInterfaceForQuestion {

	/*
	 		private Integer questionpid;
			private Quiz quizid;
			private String questionstring;
	  	
	 */
	
	
	public void save(Question question);
	public void update(Question question);
	public void delete(Question question);
	
	public Question getQuestionById(Integer questionpid);
	public List<Question> getAllQuestionsForQuiz(Integer quizid);
		
}

