package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.QuizAttemptTracking;

public interface IDaoInterfaceForQuizAttemptTracking {

	/*
	 		private Integer quizattemptpid;
			private User userid;
			private Quiz quizid;
			private Category categoryid;
			private Integer score;
	 */
	
		public void save(QuizAttemptTracking quizattempt);
		public void update(QuizAttemptTracking quizattempt);
		public void delete(QuizAttemptTracking quizattempt);
		
		public QuizAttemptTracking getQuizAttemptById(Integer quizattemptpid);
		public QuizAttemptTracking getQuizAttemptByUserIdAndQuizId(Integer userid, Integer quizid);
		public List<QuizAttemptTracking> getAllQuizAttemptsForUser(Integer userid);
		public List<QuizAttemptTracking> getAllQuizAttemptsByQuizId(Integer quizid);
		public List<QuizAttemptTracking> getAllQuizAttemptsByCategory(Integer categoryid);
		public List<QuizAttemptTracking> getAllQuizAttemptsForUserByCategory(Integer userid, Integer categoryid);
		public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDesc();
		public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForQuiz(Integer quizid);
		public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForCategory(Integer categoryid);
		public List<QuizAttemptTracking> getAllQuizAttemptsByScoreDescForQuizWithLimit(Integer quizid, Integer limit);
	
}
