package com.quiz.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForCategory;
import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
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
		
		
		
}
