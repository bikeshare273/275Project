package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.RankingDTO;
import com.quiz.dto.ResultDTO;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;
import com.quiz.utils.QuizMeUtils;


/*
 * @author
 * Puneet Popli.
 * 
 * 05/03/2015
 */


public class AttemptedQuizImpl 
{

	
	@Autowired
	IDaoInterfaceForUser userDao;
	
	@Autowired
	IDaoInterfaceForQuiz quizDao;	

	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;
	
	@Autowired
	QuizMeUtils appUtils;
	
	
	/*
	 * 
	 * Get all quiz attempted by user
	 */
	
	public List<ResultDTO> getAllQuizAttemptsForUser(Integer userid)
	{
	
		List<QuizAttemptTracking> getAllQuizList = quizAttemptTrackingDao.getAllQuizAttemptsForUser(userid);
		
		List<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
		
		for(QuizAttemptTracking attemptTrack : getAllQuizList)
		{
			Integer quizAttemptTrackingId = attemptTrack.getQuizattemptid();
			Integer quizId = attemptTrack.getQuizid();
			
			
			Quiz quiz = quizDao.getQuizById(quizId);
			QuizDTO quizDTO = new QuizDTO();
			
			RankingDTO rankDTO = new RankingDTO();
			ResultDTO resultDTO = new ResultDTO();
			
			Long rank = rankDTO.getRank();
			rankDTO.setRank(rank);
			
			//Setting quiz name, quiz creator and quiz category from quizdto
			quizDTO.setQuizname(quiz.getQuizname());
			quizDTO.setQuizcreator(quiz.getQuizcreator());
			quizDTO.setCategory(quiz.getCategory());
			
			
			//setting quizdto object in resultdto
			resultDTO.setScoreForQuiz(quizDTO);	
			resultDTO.setScore(attemptTrack.getScore());
			
			//there might be a need to change the logic for setting the rank
			resultDTO.setRankForQuiz(rankDTO);
			
			resultDTOList.add(resultDTO);
		}
		
		
		return resultDTOList;
		
	}
}
