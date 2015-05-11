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
import com.quiz.dto.SearchDTO;
import com.quiz.dto.SearchResultDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;

public class SearchImpl {

	@Autowired
	IDaoInterfaceForQuiz quizDao;

	@Autowired
	IDaoInterfaceForUser userDao;

	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;

	public ResponseEntity searchQuiz(int userid, SearchDTO searchDTO) {
	
		ResponseEntity responseEntity = null;
		List<SearchResultDTO> searchResultDTOs = new ArrayList<SearchResultDTO>();
		try {
			// validate serach criteria
			if (searchDTO == null || searchDTO.getSearchId() == null) {
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			// based on serach criteria fetch quizes
			List<Quiz> quizes = null;
			switch (Integer.parseInt(searchDTO.getSearchId())) {
			case 0:	quizes = searchQuizByCategory(searchDTO); break; // Search by Category
			case 1:	quizes = searchQuizByDiffLevel(searchDTO); break; // Search by Level
			case 2:	quizes = searchQuizByCreatedUser(searchDTO); break;// Search by User Name
			}
			// get topper for each quiz
			if (quizes == null) {
				throw new QuizAppException(400, "Search Criteria is missing");
			}
			for (Quiz quiz : quizes) {
				QuizDTO quizDTO = new QuizDTO();
				BeanUtils.copyProperties(quizDTO, quiz);
				SearchResultDTO searchResultDTO = new SearchResultDTO();
				// fetch user based on userid
				UserDTO userDTOForQuizCreator = new UserDTO();
				BeanUtils.copyProperties(userDTOForQuizCreator,
						userDao.getUserById(quiz.getQuizcreator()));
				quizDTO.setQuizcreatoruser(userDTOForQuizCreator);
				searchResultDTO.setQuizDTO(quizDTO);
				// find max scorer
				
				
				List<QuizAttemptTracking> topAttemptTrackings = quizAttemptTrackingDao
						.getAllQuizAttemptsByScoreDescForQuizWithLimit(
								quizDTO.getQuizid(), 1);
				if (topAttemptTrackings == null) {
					// quiz not attempted yet
					UserDTO userDTO = new UserDTO();
					userDTO.setEmail("Not Attempted");
					searchResultDTO.setScore(0l);
					searchResultDTO.setUserDTO(userDTO);
				} else {
					QuizAttemptTracking topAttempt = topAttemptTrackings.get(0);
					searchResultDTO.setScore(Long.parseLong(topAttempt
							.getScore().toString()));
					UserDTO userDTO = new UserDTO();
					BeanUtils.copyProperties(userDTO, userDao.getUserById(topAttempt.getUserid()));
					searchResultDTO.setUserDTO(userDTO);
				}
				
				int quizid = quizDTO.getQuizid();
				boolean attemptFlag = verifyQuizAttemptForUser(userid, quizid);
				
				if(attemptFlag) {searchResultDTO.setAttemptFlag(true);}
				else{searchResultDTO.setAttemptFlag(false);}
				
				searchResultDTOs.add(searchResultDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof QuizAppException)
				return new ResponseEntity<QuizAppException>(
						(QuizAppException) e, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<QuizAppException>(
						new QuizAppException(500,
								"Unexpected Error Encountered"),
						HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<SearchResultDTO>>(searchResultDTOs,
				HttpStatus.OK);
	}

	public List<Quiz> searchQuizByCategory(SearchDTO searchDTO)
			throws Exception {
		if (searchDTO == null || searchDTO.getSerachString() == null
				|| searchDTO.getSerachString().isEmpty()) {
			throw new QuizAppException(400, "Search Category is missing");
		}
		// get id for category

		// get list of quiz for category id
		List<Quiz> quizes = quizDao.getAllQuizzesByCategory(searchDTO
				.getSerachString());
		if (quizes == null || quizes.size() == 0) {
			throw new QuizAppException(400, "No Quizes Found for this Criteria");
		}
		return quizes;
	}

	public List<Quiz> searchQuizByDiffLevel(SearchDTO searchDTO)
			throws Exception {

		if (searchDTO == null || searchDTO.getSerachString() == null
				|| searchDTO.getSerachString().isEmpty()) {
			throw new QuizAppException(400, "Search Category is missing");
		}
		// get id for category

		// get list of quiz for category id
		List<Quiz> quizes = quizDao.getAllQuizzesByLevel(searchDTO
				.getSerachString());
		if (quizes == null || quizes.size() == 0) {
			throw new QuizAppException(400, "No Quizes Found for this Criteria");
		}
		return quizes;
	}

	public List<Quiz> searchQuizByCreatedUser(SearchDTO searchDTO)
			throws Exception {

		if (searchDTO == null || searchDTO.getSerachString() == null
				|| searchDTO.getSerachString().isEmpty()) {
			throw new QuizAppException(400, "Search Category is missing");
		}

		List<User> users = userDao.getUserByName(searchDTO.getSerachString());

		if (users == null) {
			throw new QuizAppException(400,
					"No user present with entered name.");
		}

		List<Integer> listOfUserIds = new ArrayList<Integer>();

		for (User user : users) {
			Integer userid = user.getUserid();
			if (!listOfUserIds.contains(userid)) {
				listOfUserIds.add(userid);
			}
		}

		List<Quiz> listOfQuizzes = new ArrayList<Quiz>();

		for (Integer creatorid : listOfUserIds) {
			List<Quiz> quizzes = quizDao.getAllQuizzesByCreatorId(creatorid);

			if (quizzes != null) {
				for (Quiz quiz : quizzes) {
					listOfQuizzes.add(quiz);
				}
			}

		}

		if (listOfQuizzes == null || listOfQuizzes.size() == 0) {
			throw new QuizAppException(400, "No Quizes Found for this Criteria");
		}
		return listOfQuizzes;
	}
	
	
	public boolean verifyQuizAttemptForUser(int userid, int quizid)
	{
	
		QuizAttemptTracking quizAttempt =  quizAttemptTrackingDao.getQuizAttemptByUserIdAndQuizId(userid, quizid);
		
		if(quizAttempt == null) { return false; }
		
		else return true;
		
	}

}
