package com.quiz.dto;

public class QuestionAnswerDTO {
	
	// This object will come as an embedded list of objects in QuizSubmitDTO
	
	//Following fields will come from UI.
	private Integer questionid;
	private Integer userselectedoptionoid;
	
	//Following fields will be populated in response to ui along with above fields.
	private Integer answeroptionid;
	private String userselectedoptionstring;
	private String answeroptionstring;
		
	public Integer getQuestionid() {
		return questionid;
	}
	public void setQuestionid(Integer questionid) {
		this.questionid = questionid;
	}
	public Integer getUserselectedoptionoid() {
		return userselectedoptionoid;
	}
	public void setUserselectedoptionoid(Integer userselectedoptionoid) {
		this.userselectedoptionoid = userselectedoptionoid;
	}
	public Integer getAnsweroptionid() {
		return answeroptionid;
	}
	public void setAnsweroptionid(Integer answeroptionid) {
		this.answeroptionid = answeroptionid;
	}
	public String getUserselectedoptionstring() {
		return userselectedoptionstring;
	}
	public void setUserselectedoptionstring(String userselectedoptionstring) {
		this.userselectedoptionstring = userselectedoptionstring;
	}
	public String getAnsweroptionstring() {
		return answeroptionstring;
	}
	public void setAnsweroptionstring(String answeroptionstring) {
		this.answeroptionstring = answeroptionstring;
	}
	
}
