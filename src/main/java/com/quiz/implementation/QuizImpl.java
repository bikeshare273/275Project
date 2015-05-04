package com.quiz.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForCategory;
import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuestionDTO;
import com.quiz.dto.QuizDTO;
import com.quiz.entities.Category;
import com.quiz.entities.Option;
import com.quiz.entities.Question;
import com.quiz.entities.QuestionConrrectAnswerRef;
import com.quiz.entities.Quiz;
import com.quiz.entities.User;
import com.quiz.utils.QuizMeUtils;

public class QuizImpl {

	@Autowired
	IDaoInterfaceForUser usersDao;

	@Autowired
	QuizMeUtils appUtils;

	@Autowired
	IDaoInterfaceForQuiz quizDao;

	@Autowired
	IDaoInterfaceForQuestion questionDao;

	@Autowired
	IDaoInterfaceForCategory categoryDao;

	@Autowired
	IDaoInterfaceForOption optionDao;

	@Autowired
	IDaoInterfaceForQuestionCorrectAnswer correctAnswerReferenceDao;

	static Integer InitialPopularityCount = 0;

/************************************************************************************************************/

	public QuizDTO createQuiz(QuizDTO quizDTO, Integer userid) {

		Quiz quizObject = new Quiz();

		/* Quiz Object Population */

		Integer quizid = appUtils.generateIdValue(100);
		String quizname = quizDTO.getQuizname();
		String quizdescription = quizDTO.getQuizdescription();
		String quizlevel = quizDTO.getQuizlevel();

		quizObject.setQuizid(quizid);
		quizObject.setQuizname(quizname);
		quizObject.setQuizdescription(quizdescription);
		quizObject.setQuizlevel(quizlevel);

		quizObject.setPopularitycount(InitialPopularityCount);

		User userObject = usersDao.getUserById(userid);
		Category categoryObject = categoryDao.getCategoryById(quizDTO.getCategoryid());

		quizObject.setQuizcreator(userObject);
		quizObject.setCategoryid(categoryObject);

		List<QuestionDTO> questionDTOs = quizDTO.getQuestions();

		quizDao.save(quizObject);

		createQuestions(questionDTOs, quizObject); // Fetch and create questions.

		quizDTO.setQuizid(quizid);
		
		return quizDTO;
		

	}

	public void createQuestions(List<QuestionDTO> questionDTOs, Quiz quiz) {

		for (QuestionDTO questionDTO : questionDTOs) {
			Integer questionid = appUtils.generateIdValue(200);

			Question question = new Question();

			question.setQuestionid(questionid);
			question.setQuizid(quiz);
			question.setQuestionstring(questionDTO.getQuestionstring());

			questionDao.save(question);

			String correctionOptionString = questionDTO	.getCorrectionoptionstring();

			List<String> optionStrings = questionDTO.getOptionStringFromUI();
			
			createOptions(optionStrings, question, correctionOptionString, quiz); // Fetch and create options

		}

	}

	public void createOptions(List<String> optionsStrings, Question question,
			String correctionOptionString, Quiz quiz) {

		for (String optionString : optionsStrings) {
			Integer optionid = appUtils.generateIdValue(300);

			Option option = new Option();

			option.setOptionid(optionid);
			option.setOptionvalue(optionString);
			option.setQuestionid(question);
			option.setQuizid(quiz);

			optionDao.save(option);

			if (option.getOptionvalue() == correctionOptionString) 
			{
				createCorrectAnswerReference(question, option); // Fetch and create CorrectAnswerReference
			}

		}

	}

	public void createCorrectAnswerReference(Question question, Option option)

	{
		Integer questioncorrectanswerpid = appUtils.generateIdValue(400);
		QuestionConrrectAnswerRef object = new QuestionConrrectAnswerRef();

		object.setQuestioncorrectanswerpid(questioncorrectanswerpid);
		object.setQuestionid(question);
		object.setOptionid(option);
	}

/************************************************************************************************************/	
	
	
}
