package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuestionAnswerDTO;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.QuizStatDTO;
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
	public QuizStatDTO getQuizStat(QuizDTO quizDTO)
	{
		List<QuizAttemptTracking> quizAttemptList=new ArrayList<QuizAttemptTracking>();
		QuizStatDTO quizStatDTO=new QuizStatDTO();
		try{
			quizAttemptList=quizAttemptTrackingDao.getAllQuizAttemptsByQuizId(quizDTO.getQuizid());
			
			long totalQuizTakers = 0l;
			if(quizAttemptList != null && quizAttemptList.size() > 0){
				totalQuizTakers = quizAttemptList.size();
			}
			long lowestScore=Long.MAX_VALUE;
			long highestScore=0l;
			int sum=0;
			double averageScore = 0.0;
			if(quizAttemptList != null){
				for (QuizAttemptTracking quizAttemptTracking : quizAttemptList) 
				{
					if(quizAttemptTracking.getScore()<lowestScore)
					lowestScore=quizAttemptTracking.getScore();
				}
				for (QuizAttemptTracking quizAttemptTracking : quizAttemptList) 
				{
					if(quizAttemptTracking.getScore()>highestScore)
					highestScore=quizAttemptTracking.getScore();
				}
				for (QuizAttemptTracking quizAttemptTracking : quizAttemptList) 
				{
					sum=sum+quizAttemptTracking.getScore();
				}
				if(quizAttemptList.size() > 0){
					averageScore=sum/quizAttemptList.size();	
				}
			}
			
			quizStatDTO.setAverageScore(averageScore);
			if(lowestScore==Long.MAX_VALUE){
				lowestScore = 0l;
			}
			quizStatDTO.setHighestScore(highestScore);
			quizStatDTO.setLowestScore(lowestScore);
			BeanUtils.copyProperties(quizDTO, quizDao.getQuizById(quizDTO.getQuizid()));
			quizStatDTO.setQuiz(quizDTO);
			quizStatDTO.setTotalQuizTakers(totalQuizTakers);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return quizStatDTO;

			
	}

	/******************************************************************************************/

}