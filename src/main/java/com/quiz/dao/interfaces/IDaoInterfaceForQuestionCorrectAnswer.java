package com.quiz.dao.interfaces;

import com.quiz.entities.QuestionConrrectAnswerRef;

public interface IDaoInterfaceForQuestionCorrectAnswer {

	/*
	 		private Integer questioncorrectanswerpid;
			private Question questionid;
			private Option optionid;

 */

	
		public void save(QuestionConrrectAnswerRef entity);
		public void update(QuestionConrrectAnswerRef entity);
		public void delete(QuestionConrrectAnswerRef entity);
	
		public IDaoInterfaceForQuestionCorrectAnswer getOptionById(Integer questioncorrectanswerpid);
		public IDaoInterfaceForQuestionCorrectAnswer getCorrectOptionForQuestion(Integer questionid);
		
	
}
