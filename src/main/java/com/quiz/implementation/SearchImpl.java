package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForCategory;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.SearchDTO;
import com.quiz.dto.SearchResultDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Category;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;

public class SearchImpl {

	@Autowired
	IDaoInterfaceForQuiz quizDao;
	
	@Autowired
	IDaoInterfaceForCategory categoryDao;
	
	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;
	
	public ResponseEntity searchQuiz(SearchDTO searchDTO){
		ResponseEntity responseEntity = null;
		List<SearchResultDTO> searchResultDTOs = new ArrayList<SearchResultDTO>();
		try{
			//validate serach criteria
			if(searchDTO == null || searchDTO.getSearchId() == null ){
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			//based on serach criteria fetch quizes
			List<Quiz> quizes = null;
			switch(Integer.parseInt(searchDTO.getSearchId())){
				case 0: quizes = searchQuizByCategory(searchDTO);
			}
			//get topper for each quiz
			if(quizes == null){
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			for(Quiz quiz : quizes){
				QuizDTO quizDTO = new QuizDTO();
				BeanUtils.copyProperties(quizDTO, quiz);
				SearchResultDTO searchResultDTO = new SearchResultDTO();
				searchResultDTO.setQuizDTO(quizDTO);
				//find max scorer
				List<QuizAttemptTracking> topAttemptTrackings = quizAttemptTrackingDao.getAllQuizAttemptsByScoreDescForQuizWithLimit(quizDTO.getQuizid(), 1);
				if(topAttemptTrackings == null){
					//quiz not attempted yet
					UserDTO userDTO = new UserDTO();
					userDTO.setEmail("Not Attempted");
					searchResultDTO.setScore(0l);
					searchResultDTO.setUserDTO(userDTO);
				}else{
					QuizAttemptTracking topAttempt = topAttemptTrackings.get(0);
					searchResultDTO.setScore(Long.parseLong(topAttempt.getScore().toString()));
					UserDTO userDTO = new UserDTO();
					BeanUtils.copyProperties(userDTO, topAttempt.getUserid());
					searchResultDTO.setUserDTO(userDTO);
				}
				searchResultDTOs.add(searchResultDTO);
			}
		}catch(Exception e){ 
			e.printStackTrace();
			if(e instanceof QuizAppException)
				return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<SearchResultDTO>>(searchResultDTOs, HttpStatus.OK);
	}
	
	
	public List<Quiz> searchQuizByCategory(SearchDTO searchDTO) throws Exception{
		if(searchDTO == null || searchDTO.getSerachString() == null || searchDTO.getSerachString().isEmpty()){
			throw new QuizAppException(400, "Search Category is missing");
		}
		//get id for category
		Category category = categoryDao.getCategoryByName(searchDTO.getSerachString());
		if(category == null){
			throw new QuizAppException(400, "Search Category not found");
		}
		//get list of quiz for category id
		List<Quiz> quizes = quizDao.getAllQuizzesByCategory(category.getCategoryid());
		if(quizes == null && quizes.size()==0){
			throw new QuizAppException(400, "No Quizes Found for this Criteria");
		}
		return quizes;
	}
	
}
