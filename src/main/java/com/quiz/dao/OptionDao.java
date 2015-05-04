package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.entities.Option;

@Transactional
public class OptionDao implements IDaoInterfaceForOption {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Option option) {

		hibernateTemplate.save(option);
	}

	@Override
	public void update(Option option) {

		hibernateTemplate.update(option);
	}

	@Override
	public void delete(Option option) {

		hibernateTemplate.delete(option);
	}

	@Override
	public Option getOptionById(Integer optionid) {

		String query = "from Option o where o.optionid = ?";
		
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>) hibernateTemplate.find(query, optionid);
		
		if(options.isEmpty()) { return null; }
		
		return options.get(0);
		
	}

	@Override
	public List<Option> getAllOptionsForQuiz(Integer quizid) {

		String query = "from Option o where o.quizid.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>) hibernateTemplate.find(query, quizid);
		
		if(options.isEmpty()) { return null; }
		
		return options;
		
	}

	@Override
	public List<Option> getAllOptionsForQuestion(Integer questionid) {
		
		String query = "from Option o where o.questionid.questionid = ?";
		
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>) hibernateTemplate.find(query, questionid);
		
		if(options.isEmpty()) { return null; }
		
		return options;
	}

}
