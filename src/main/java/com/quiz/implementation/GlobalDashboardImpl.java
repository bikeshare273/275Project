package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.RankingDTO;
import com.quiz.dto.ResultDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;

/*
 * 
 * @author: Puneet Popli.
 * 05/04/2015
 */


public class GlobalDashboardImpl 
{

	@Autowired
	IDaoInterfaceForUser userDao;
	
	
	@Autowired
	IDaoInterfaceForQuizAttemptTracking attemptTrackingDao;
	
	/*
	 * 
	 * 1. Get top score user.
	 * 
	 */
	
	
	public ResponseEntity getTopScoreUsers()
	{
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		
		//list of all the users based on ranking will be stored in the userList
		List<User> userList = userDao.getGlobalRanks(); 
		int numberOfTopScorer = 20;
		if(userList.size() < numberOfTopScorer){
			numberOfTopScorer = userList.size();
		}
		
		for(int i=0; i<numberOfTopScorer; i++)
		{
			try{
				User user = userList.get(i);
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(userDTO, user);
				userDTOList.add(userDTO);
			}catch(Exception e){ 
				e.printStackTrace();
				if(e instanceof QuizAppException)
					return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
				else
					return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<List<UserDTO>>(userDTOList,HttpStatus.OK);
		
	}
	
	
	/*
	 * 2. Get top score user for category
	 * 
	 */
	
	public ResponseEntity getTopScoreUserForCategory(RankingDTO rankingDTO)
	{
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		
		List<QuizAttemptTracking> quizTrackingList = attemptTrackingDao.getAllQuizAttemptsByScoreDescForCategory
				(rankingDTO.getCategory());
		
		int numberOfTopScorer = 20;
		if(quizTrackingList.size() < numberOfTopScorer){
			numberOfTopScorer = quizTrackingList.size();
		}
		
		for(int i=0; i<numberOfTopScorer; i++)
		{
			try{
				UserDTO userDTO = new UserDTO();
				QuizAttemptTracking quizAttempTrackingObject = quizTrackingList.get(i);
				Integer userId = quizAttempTrackingObject.getUserid();
				User user = userDao.getUserById(userId);
				BeanUtils.copyProperties(userDTO, user);
				userDTO.setTotalScore(quizAttempTrackingObject.getScore());
				userDTOList.add(userDTO);
			}catch(Exception e){ 
				e.printStackTrace();
				if(e instanceof QuizAppException)
					return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
				else
					return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
	}
	
}
