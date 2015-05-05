package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.RankingDTO;
import com.quiz.dto.ResultDTO;
import com.quiz.dto.SearchResultDTO;
import com.quiz.dto.UserDTO;
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
	
	public ResponseEntity getAllQuizAttemptsForUser(Integer userid)
	{
	
		List<QuizAttemptTracking> getAllQuizList = quizAttemptTrackingDao.getAllQuizAttemptsForUser(userid);
		
		List<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
		
		try{
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
				BeanUtils.copyProperties(quizDTO, quiz);
				
				//setting quizdto object in resultdto
				resultDTO.setScoreForQuiz(quizDTO);	
				resultDTO.setScore(attemptTrack.getScore());
				
				//there might be a need to change the logic for setting the rank
				resultDTO.setRankForQuiz(rankDTO);
				
				//user object
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(userDTO, userDao.getUserById(quizDTO.getQuizcreator()));
				quizDTO.setQuizcreatoruser(userDTO);
				
				resultDTOList.add(resultDTO);
			}
		}catch(Exception e){ 
			e.printStackTrace();
			if(e instanceof QuizAppException)
				return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ResultDTO>>(resultDTOList, HttpStatus.OK);
		
	}
}
