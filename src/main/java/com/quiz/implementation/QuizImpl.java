package com.quiz.implementation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.QuestionDTO;
import com.quiz.dto.QuizDTO;
import com.quiz.entities.Option;
import com.quiz.entities.Question;
import com.quiz.entities.QuestionConrrectAnswerRef;
import com.quiz.entities.Quiz;
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
	IDaoInterfaceForOption optionDao;

	@Autowired
	IDaoInterfaceForQuestionCorrectAnswer correctAnswerReferenceDao;
	
	@Autowired
	UserImpl userImpl;

	static int InitialPopularityCount = 0;
	static int quizCreationFlag = 0;
	static int noScoreToReportFlag = 0;
	
/************************************************************************************************************/

	public QuizDTO createQuiz(QuizDTO quizDTO, Integer userid) {

		Quiz quizObject = new Quiz();

		/* Quiz Object Population */

		Integer quizid = appUtils.generateIdValue(100);
		String quizname = quizDTO.getQuizname();
		String quizdescription = quizDTO.getQuizdescription();
		String quizlevel = quizDTO.getQuizlevel();
		String category = quizDTO.getCategory();

		quizObject.setQuizid(quizid);
		quizObject.setQuizname(quizname);
		quizObject.setQuizdescription(quizdescription);
		quizObject.setQuizcreator(userid);
		quizObject.setQuizlevel(quizlevel);
		quizObject.setCategory(category);
		quizObject.setPopularitycount(InitialPopularityCount);
			
		List<QuestionDTO> questionDTOs = quizDTO.getQuestions();

		quizDao.save(quizObject);

		createQuestions(questionDTOs, quizid); // Fetch and create questions.

		quizDTO.setQuizid(quizid);
		
		userImpl.updateUserQuizAndScoreProfile(userid, noScoreToReportFlag, quizCreationFlag);
		
		return quizDTO;
	}

	public void createQuestions(List<QuestionDTO> questionDTOs, Integer quizid) {

		for (QuestionDTO questionDTO : questionDTOs) {
			
			Integer questionid = appUtils.generateIdValue(200);

			Question question = new Question();

			question.setQuestionid(questionid);
			question.setQuizid(quizid);
			question.setQuestionstring(questionDTO.getQuestionstring());

			questionDao.save(question);

			String correctionOptionString = questionDTO	.getCorrectionoptionstring();

			List<String> optionStrings = questionDTO.getOptionStringFromUI();
			
			createOptions(optionStrings, questionid, correctionOptionString, quizid); // Fetch and create options

		}

	}

	public void createOptions(List<String> optionsStrings, Integer questionid,
			String correctionOptionString, Integer quizid) {

		for (String optionString : optionsStrings) {
			Integer optionid = appUtils.generateIdValue(300);

			Option option = new Option();

			option.setOptionid(optionid);
			option.setOptionvalue(optionString);
			option.setQuestionid(questionid);
			option.setQuizid(quizid);

			optionDao.save(option);

			if (correctionOptionString.equalsIgnoreCase(option.getOptionvalue())) 
			{
				createCorrectAnswerReference(questionid, optionid); // Fetch and create CorrectAnswerReference
			}

		}

	}

	public void createCorrectAnswerReference(Integer questionid, Integer optionid)

	{
		
		Integer questioncorrectanswerid = appUtils.generateIdValue(400);
		QuestionConrrectAnswerRef object = new QuestionConrrectAnswerRef();

		object.setQuestioncorrectanswerid(questioncorrectanswerid);
		object.setQuestionid(questionid);
		object.setOptionid(optionid);
		
		correctAnswerReferenceDao.save(object);
		
	}

/************************************************************************************************************/	
	
	public QuizDTO getQuiz(Integer quizid){
			
		
		Quiz quiz = quizDao.getQuizById(quizid);
		QuizDTO quizDTO = new QuizDTO();
				
		try {
			BeanUtils.copyProperties(quizDTO, quiz);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<QuestionDTO> questions = fetchQuestionsAndOptions(quizid);
		
		quizDTO.setQuestions(questions);
		
		return quizDTO;
		
	}
	
	public List<QuestionDTO> fetchQuestionsAndOptions(Integer quizid){
		
		List<Question> questions = questionDao.getAllQuestionsForQuiz(quizid);
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		
		
		for(Question question : questions)
		{
			Integer questionid = question.getQuestionid();
			
			List<Option> options = optionDao.getAllOptionsForQuestion(questionid);
			
			for(Option option : options)
			{
				option.setQuizid(null);
				option.setQuestionid(null);
			}
			
			
			QuestionConrrectAnswerRef correctansweroption = correctAnswerReferenceDao.getCorrectOptionForQuestion(questionid); 
			
			Integer optionid = correctansweroption.getOptionid();
			
			Option option = optionDao.getOptionById(optionid);
				
			QuestionDTO questionDTO = new QuestionDTO();
			
			questionDTO.setQuestionid(questionid);
			questionDTO.setQuestionstring(question.getQuestionstring());
			questionDTO.setOptions(options);
			questionDTO.setCorrectionoptionstring(option.getOptionvalue());
			questionDTO.setCorrectionoptionid(option.getOptionid());
			
			questionDTOs.add(questionDTO);
			
		}
		return questionDTOs;
	}
	
	/************************************************************************************************************/
}
