package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.Quiz;

public interface IDaoInterfaceForQuiz {

	
		/*
		 	private Integer quizid;
			private String quizname;
			private String quizdescription;
			private Category categoryid;
			private User quizcreator;
			private QuizLevel quizlevel;
			private Integer popularitycount;
		
		 */
	
	
	public void save(Quiz quiz);
	public void update(Quiz quiz);
	public void delete(Quiz quiz);
	
	public Quiz getQuizById(Integer quizid);
	public List<Quiz> getQuizByName(String quizname);
	public List<Quiz> getAllQuizzesByCategory(String category);
	public List<Quiz> getAllQuizzesByCreatorId(Integer creatorid);
	public List<Quiz> getAllQuizzesByLevel(String quizlevel);
	public List<Quiz> getAllQuzzesByCategoryAndLevel(String category, String quizlevel);
	public List<Quiz> getAllQuizzesByPopularityDesc();
		
}