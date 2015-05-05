package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
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
		
		for(User user: userList)
		{
		
			UserDTO userDTO = new UserDTO();
		
			userDTO.setName(user.getName());
			userDTO.setTotalScore(user.getTotalScore());
			userDTO.setCountry(user.getCountry());
			
			userDTOList.add(userDTO);
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
		
		for(QuizAttemptTracking quizAttempTrackingObject: quizTrackingList)
		{
			
			UserDTO userDTO = new UserDTO();
			
			Integer userId = quizAttempTrackingObject.getUserid();
			
			User user = userDao.getUserById(userId);
			
			userDTO.setName(user.getName());
			userDTO.setCountry(user.getCountry());
			userDTO.setTotalScore(user.getTotalScore());
			
			
			userDTOList.add(userDTO);
		}
		
		
		return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
	}
	
}
