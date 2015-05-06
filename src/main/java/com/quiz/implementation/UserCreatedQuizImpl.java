package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.ResultDTO;
import com.quiz.dto.TopperDTO;
import com.quiz.dto.UserCreatedDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;


public class UserCreatedQuizImpl 
{

	@Autowired
	IDaoInterfaceForQuiz quizDao;
	
	@Autowired
	IDaoInterfaceForUser userDao;
	
	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;

	@Autowired
	QuizImpl quizImpl;

	
	public ResponseEntity getUserCreatedQuiz(Integer creatorid)
	{
		List<Quiz> quizList = quizDao.getAllQuizzesByCreatorId(creatorid);
		
		List<UserCreatedDTO> userCreatedDTOList = new ArrayList<UserCreatedDTO>();
		
		for(Quiz quiz : quizList)
		{
			
			
			UserCreatedDTO userCreatedDTO = new UserCreatedDTO();
			
			userCreatedDTO.setCategory(quiz.getCategory());
			
			User user = userDao.getUserById(quiz.getQuizcreator());
			
			userCreatedDTO.setQuizCreator(user.getName());
			
			userCreatedDTO.setQuizName(quiz.getQuizname());
			
			TopperDTO topperDTO = quizImpl.getMaxScoreAndTopperByQuiz(quiz.getQuizid());
			
			if(topperDTO!=null)
			{
				userCreatedDTO.setTopper(topperDTO.getTopperName());
				
				userCreatedDTO.setMaxScore(topperDTO.getMaxscore());
			}
			else
			{
				userCreatedDTO.setTopper("NO RECORD");
				userCreatedDTO.setMaxScore(0);
			}
			userCreatedDTOList.add(userCreatedDTO);
			
			
		}
		
		
		
		
		
		return new ResponseEntity<List<UserCreatedDTO>>(userCreatedDTOList,HttpStatus.OK);
	}
	

}
