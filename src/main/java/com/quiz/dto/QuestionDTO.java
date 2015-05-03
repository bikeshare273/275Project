package com.quiz.dto;

import java.util.List;

import com.quiz.entities.Option;
import com.quiz.entities.Quiz;

public class QuestionDTO {
	
	private Integer questionid;
	private Quiz quizid;
	private String questionstring;
	private List<String> optionStringFromUI;
	private List<Option> options;
	private Integer correctionoptionid;
	private Integer useransweredoptionid;
	
	public Integer getQuestionid() {
		return questionid;
	}
	public void setQuestionid(Integer questionid) {
		this.questionid = questionid;
	}
	public Quiz getQuizid() {
		return quizid;
	}
	public void setQuizid(Quiz quizid) {
		this.quizid = quizid;
	}
	public String getQuestionstring() {
		return questionstring;
	}
	public void setQuestionstring(String questionstring) {
		this.questionstring = questionstring;
	}
	public List<String> getOptionStringFromUI() {
		return optionStringFromUI;
	}
	public void setOptionStringFromUI(List<String> optionStringFromUI) {
		this.optionStringFromUI = optionStringFromUI;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public Integer getCorrectionoptionid() {
		return correctionoptionid;
	}
	public void setCorrectionoptionid(Integer correctionoptionid) {
		this.correctionoptionid = correctionoptionid;
	}
	public Integer getUseransweredoptionid() {
		return useransweredoptionid;
	}
	public void setUseransweredoptionid(Integer useransweredoptionid) {
		this.useransweredoptionid = useransweredoptionid;
	}
}
