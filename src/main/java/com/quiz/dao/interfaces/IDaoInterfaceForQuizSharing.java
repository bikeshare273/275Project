package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.QuizSharing;


public interface IDaoInterfaceForQuizSharing {

	/*
	 	private Integer quizsharingpid;
		private User userid;
		private Integer recommenderid;
		private Boolean completedflag;
		
	 */
	
	public void save(QuizSharing quizSharingEntry);
	public void update(QuizSharing quizSharingEntry);
	public void delete(QuizSharing quizSharingEntry);
	
	public QuizSharing getQuizSharingById(Integer quizSharingid);
	public List<QuizSharing> getQuizSharingByUserId(Integer userid);
	public List<QuizSharing> getQuizSharingByQuizId(Integer quizid);
	public QuizSharing getQuizSharingByUserIdAndQuizId(Integer userid, Integer quizid);
	public List<QuizSharing> getQuizSharingByUserIdAndQuizIdAndRecommenderId(Integer userid, Integer quizid, Integer recommenderid);
	public List<QuizSharing> getUnttemptedQuizSharings(Integer userid);
	public List<QuizSharing> getAllQuizSharingEntriesByRecommender(Integer recommenderid);
}
