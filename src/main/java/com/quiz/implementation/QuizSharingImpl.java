package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizSharing;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizSharing;
import com.quiz.utils.QuizMeUtils;

public class QuizSharingImpl {

	@Autowired
	IDaoInterfaceForQuizSharing quizSharingDao;
	
	@Autowired
	IDaoInterfaceForQuiz quizDao;

	@Autowired
	QuizMeUtils appUtils;

/*************************************************************************************************************/

	public void addQuizSharingEntry(Integer userid, Integer quizid, Integer recommenderid) {

		boolean recoFlag = verifyQuizSharingEntiesForUserWithQuizExists(userid,	quizid);

		if (recoFlag == true) {
			updateQuizSharingAsNew(userid, quizid, recommenderid);
		} else {
			QuizSharing quizSharingEntry = new QuizSharing();

			Integer quizSharingEntryId = appUtils.generateIdValue(1000);

			quizSharingEntry.setQuizsharingid(quizSharingEntryId);
			quizSharingEntry.setUserid(userid);
			quizSharingEntry.setQuizid(quizid);
			quizSharingEntry.setCompletedflag(false);
			quizSharingEntry.setRecommenderid(recommenderid);

			quizSharingDao.save(quizSharingEntry);
		}
	}

	public void updateRecommendations(Integer userid, Integer quizid) {
	
		boolean recoFlag = verifyQuizSharingEntiesForUserWithQuizExists(userid,	quizid);

		if (recoFlag) {
			updateQuizSharingAsCompleted(userid, quizid);
		}

	}

	/*************************************************************************************************************/

	public void updateQuizSharingAsCompleted(Integer userid, Integer quizid) {
	
		QuizSharing quizSharingEntry = quizSharingDao.getQuizSharingByUserIdAndQuizId(userid, quizid);

		if (quizSharingEntry.isCompletedflag() == false) {
			quizSharingEntry.setCompletedflag(true);
		}

		quizSharingDao.update(quizSharingEntry);
	}

	public void updateQuizSharingAsNew(Integer userid, Integer quizid, Integer recommenderid) {
		QuizSharing quizSharingEntry = quizSharingDao.getQuizSharingByUserIdAndQuizId(userid, quizid);

		if (quizSharingEntry.isCompletedflag() == true) {
			quizSharingEntry.setCompletedflag(false);
		}
		quizSharingEntry.setRecommenderid(recommenderid);

		quizSharingDao.update(quizSharingEntry);
	}

	/*************************************************************************************************************/

	public boolean verifyQuizSharingForUserExists(Integer userid) {
		List<QuizSharing> quizSharingEntries = quizSharingDao.getQuizSharingByUserId(userid);

		if (quizSharingEntries == null) {
			return false;
		}

		return true;
	}

	public boolean verifyUnseenRecommendationsExistForUser(Integer userid) {
		List<QuizSharing> quizSharingEntries = quizSharingDao.getUnttemptedQuizSharings(userid);
			
		if (quizSharingEntries == null) {
			return false;
		}

		return true;
	}

	/*************************************************************************************************************/

	public boolean verifyQuizSharingEntiesForUserWithQuizExists(Integer userid,	Integer quizid) {

		QuizSharing quizSharingEntries = quizSharingDao.getQuizSharingByUserIdAndQuizId(userid, quizid);

		if (quizSharingEntries == null) {
			return false;
		}

		return true;
	}

	/*************************************************************************************************************/

	public List<Quiz> getUnattemptedQuizSharingEntries(Integer userid) {
	
		List<QuizSharing> quizSharingEntries = quizSharingDao.getUnttemptedQuizSharings(userid);
			

		if (quizSharingEntries == null) {
			return null;
		}

		List<Integer> listOfUnAttemptedQuizIds = new ArrayList<Integer>();

		for (QuizSharing quizSharingEntry : quizSharingEntries) {
			
			listOfUnAttemptedQuizIds.add(quizSharingEntry.getQuizid());
			}
			
		List<Quiz> quizzes = new ArrayList<Quiz>();

		for (Integer quizid : listOfUnAttemptedQuizIds) {
			
			Quiz quiz = quizDao.getQuizById(quizid);

			if (quiz != null) {
				quizzes.add(quiz);
			}

		}

		return quizzes;

	}	
	/*************************************************************************************************************/

	public void deleteQuizSharingEntries(Integer quizid) {

		List<QuizSharing> quizSharingEntries = quizSharingDao.getQuizSharingByQuizId(quizid);
			
		if (quizSharingEntries != null) {
			for (QuizSharing quizSharingEntry : quizSharingEntries) {
				quizSharingDao.delete(quizSharingEntry);
			}

		}

	}


}
