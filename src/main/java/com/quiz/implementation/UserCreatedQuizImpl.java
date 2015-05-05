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
import com.quiz.dto.UserDTO;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;


public class UserCreatedQuizImpl 
{

	@Autowired
	IDaoInterfaceForQuiz quizDao;
	
	@Autowired
	IDaoInterfaceForUser userDao;
	
	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;

	public ResponseEntity getUserCreatedQuiz(Integer creatorid)
	{
		List<Quiz> quizList = quizDao.getAllQuizzesByCreatorId(creatorid);
		List<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
		int numberOfQuiz = 20;

		if(quizList.size() < numberOfQuiz)
		{
			numberOfQuiz=quizList.size();
		}

		for(int i=0; i<numberOfQuiz; i++)
		{
			try{
				Quiz quiz = quizList.get(i);

				ResultDTO resultDTO = new ResultDTO();
				QuizDTO quizDTO = new QuizDTO();
				UserDTO userDTO = new UserDTO();
				
				
				BeanUtils.copyProperties(quizDTO, quiz);
				//get the name of quiz creator
				BeanUtils.copyProperties(userDTO, userDao.getUserById(quizDTO.getQuizcreator()));
				quizDTO.setQuizcreatoruser(userDTO);
				
				
				resultDTO.setScoreForQuiz(quizDTO);
				
				resultDTOList.add(resultDTO);
			}
			catch(Exception e){
				e.printStackTrace();
				if(e instanceof QuizAppException)
					return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
				else
					return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}

		return new ResponseEntity<List<ResultDTO>>(resultDTOList,HttpStatus.OK);

	}

}
