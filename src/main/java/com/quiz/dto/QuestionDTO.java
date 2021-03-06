package com.quiz.dto;

import java.util.List;

import com.quiz.entities.Option;

public class QuestionDTO {
	
	private Integer no;
	private Integer questionid;
	private Integer quizid;
	private String questionstring;
	private List<String> optionStringFromUI;
	private List<Option> options;
	private String correctionoptionstring;
	private Integer correctionoptionid;
	private Integer useransweredoptionid;
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getQuestionid() {
		return questionid;
	}
	public void setQuestionid(Integer questionid) {
		this.questionid = questionid;
	}
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
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
	public String getCorrectionoptionstring() {
		return correctionoptionstring;
	}
	public void setCorrectionoptionstring(String correctionoptionstring) {
		this.correctionoptionstring = correctionoptionstring;
	}
	public Integer getUseransweredoptionid() {
		return useransweredoptionid;
	}
	public void setUseransweredoptionid(Integer useransweredoptionid) {
		this.useransweredoptionid = useransweredoptionid;
	}
	public Integer getCorrectionoptionid() {
		return correctionoptionid;
	}
	public void setCorrectionoptionid(Integer correctionoptionid) {
		this.correctionoptionid = correctionoptionid;
	}
}
