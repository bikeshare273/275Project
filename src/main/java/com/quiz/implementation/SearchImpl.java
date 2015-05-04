package com.quiz.implementation;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.SearchDTO;
import com.quiz.dto.SearchResultDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;

public class SearchImpl {

	@Autowired
	IDaoInterfaceForQuiz quizDao;
		
	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;
	
	public ResponseEntity searchQuiz(SearchDTO searchDTO){
		ResponseEntity responseEntity = null;
		SearchResultDTO searchResultDTO = new SearchResultDTO();
		try{
			//validate serach criteria
			if(searchDTO == null || searchDTO.getSearchId() == null ){
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			//based on serach criteria fetch quizes
			List<Quiz> quizes = null;
			switch(searchDTO.getSearchId()){
				case 0: quizes = searchQuizByCategory(searchDTO);
			}
			//get topper for each quiz
			if(quizes == null){
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			for(Quiz quiz : quizes){
				QuizDTO quizDTO = new QuizDTO();
				BeanUtils.copyProperties(quizDTO, quiz);
				searchResultDTO.setQuizDTO(quizDTO);
				//find max scorer
				System.out.println("quizAttemptTrackingDao quizid "+quizDTO.getQuizid());
				List<QuizAttemptTracking> topAttemptTrackings = quizAttemptTrackingDao.getAllQuizAttemptsByScoreDescForQuizWithLimit(quizDTO.getQuizid(), 1);
				if(topAttemptTrackings == null){
					//quiz not attempted yet
					searchResultDTO.setScore(0l);
				}else{
					QuizAttemptTracking topAttempt = topAttemptTrackings.get(0);
					searchResultDTO.setScore(Long.parseLong(topAttempt.getScore().toString()));
					UserDTO userDTO = new UserDTO();
					BeanUtils.copyProperties(userDTO, topAttempt.getUserid());
					searchResultDTO.setUserDTO(userDTO);
				}
			}
		}catch(Exception e){ 
			e.printStackTrace();
			if(e instanceof QuizAppException)
				return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<SearchResultDTO>(searchResultDTO, HttpStatus.OK);
	}
	
	
	public List<Quiz> searchQuizByCategory(SearchDTO searchDTO) throws Exception{
		if(searchDTO == null || searchDTO.getSerachString() == null || searchDTO.getSerachString().isEmpty()){
			throw new QuizAppException(400, "Search Category is missing");
		}
		/*get id for category
		Category category = categoryDao.getCategoryByName(searchDTO.getSerachString());
		if(category == null){
			throw new QuizAppException(400, "Search Category not found");
		}*/
		//get list of quiz for category id
		List<Quiz> quizzes = quizDao.getAllQuizzesByCategory(searchDTO.getSerachString());
		if(quizzes == null){
			throw new QuizAppException(400, "No Quizes Found for this Criteria");
		}
		return quizzes;
	}
	
}
