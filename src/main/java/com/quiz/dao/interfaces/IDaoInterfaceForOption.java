package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.Option;
import com.quiz.entities.Question;
import com.quiz.entities.Quiz;

public interface IDaoInterfaceForOption {

	/*
	 	private Integer optionpid;
		private Quiz quizid;
		private Question questionid;
		private String optionvalue;
	 */
	
	public void save(Option option);
	public void update(Option option);
	public void delete(Option option);
	
	public Option getOptionById(Integer optionpid);
	public List<Option> getAllOptionsForQuiz(Integer quizid);
	public List<Option> getAllOptionsForQuestion(Integer questionid);
	
}
