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
	
	public QuizSharing getQuizSharingEntryById(Integer quizsharingpid);
	public List<QuizSharing> getAllQuizSharingEntriesForUser(Integer userid);
	public List<QuizSharing> getAllPendingQuizSharingEntriesForUser(Integer userid);
	public List<QuizSharing> getAllQuizSharingEntriesByRecommender(Integer recommenderid);
}
