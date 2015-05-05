package com.quiz.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuestionAnswerDTO;
import com.quiz.dto.QuizSubmitDTO;
import com.quiz.entities.Option;
import com.quiz.entities.QuestionConrrectAnswerRef;
import com.quiz.entities.Quiz;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;
import com.quiz.utils.QuizMeUtils;

public class QuizResultsImpl {

	@Autowired
	IDaoInterfaceForUser usersDao;

	@Autowired
	QuizMeUtils appUtils;

	@Autowired
	IDaoInterfaceForQuiz quizDao;

	@Autowired
	IDaoInterfaceForQuestion questionDao;

	@Autowired
	IDaoInterfaceForOption optionDao;

	@Autowired
	IDaoInterfaceForQuestionCorrectAnswer correctAnswerReferenceDao;

	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;
	
	@Autowired
	UserImpl userImpl;
	
	@Autowired
	QuizSharingImpl quizSharingImpl;

	static int quizSubmissionFlag = 1;
		
	/******************************************************************************************/

	public QuizSubmitDTO evaluatteQuiz(Integer userid, QuizSubmitDTO submittedQuiz){

		
		List<QuestionAnswerDTO> questionanswers = submittedQuiz.getSetOfQuestionAndAnswers();

		int totalScoreForQuiz = 0;

		for(QuestionAnswerDTO qa : questionanswers )
		{

			int questionid = qa.getQuestionid();
			System.out.println("Question ID => " + questionid);
			int selectedoptionid = qa.getUserselectedoptionoid();
			System.out.println("Selected Option ID => " + selectedoptionid);
			QuestionConrrectAnswerRef qaref = correctAnswerReferenceDao.getCorrectOptionForQuestion(questionid);
			int correctoptionid = qaref.getOptionid();
			System.out.println("Correct Option ID => " + correctoptionid);

			if(selectedoptionid == correctoptionid) 
			{ 

				totalScoreForQuiz  = totalScoreForQuiz + 5 ; 
				System.out.println("Updated Total Score => " + totalScoreForQuiz );
			}

			Option selectedOption = optionDao.getOptionById(selectedoptionid);
			Option correctOption = optionDao.getOptionById(correctoptionid);

			qa.setUserselectedoptionstring(selectedOption.getOptionvalue()); // User selected option string
			qa.setAnsweroptionstring(correctOption.getOptionvalue());		// Correct option string

		}

		submittedQuiz.setTotalScoreForquiz(totalScoreForQuiz);
		submittedQuiz.setSetOfQuestionAndAnswers(questionanswers);

		submittedQuiz.setCategoryRank(0); // Category wise ranking
		submittedQuiz.setGlobalRank(0);  // Global Ranking

		int quizid = submittedQuiz.getQuizid();

		updateAttemptTracker(userid, quizid, totalScoreForQuiz);
		userImpl.updateUserQuizAndScoreProfile(userid, totalScoreForQuiz, quizSubmissionFlag );
		
		quizSharingImpl.updateQuizSharingEntry(userid, quizid);


		return submittedQuiz;
	}

	/******************************************************************************************/

	public void updateAttemptTracker(int userid, int quizid, int score)
	{

		QuizAttemptTracking quizAttempt = new QuizAttemptTracking();

		Quiz quiz = quizDao.getQuizById(quizid);
		String category = quiz.getCategory(); 

		int quizattemptid = appUtils.generateIdValue(500); 

		quizAttempt.setQuizattemptid(quizattemptid);
		quizAttempt.setUserid(userid);
		quizAttempt.setQuizid(quizid);
		quizAttempt.setCategory(category);
		quizAttempt.setScore(score);

		quizAttemptTrackingDao.save(quizAttempt);

	}

	/******************************************************************************************/

}