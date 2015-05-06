package com.quiz.dto;

public class UserCreatedDTO 
{

	Integer quizId;
	String quizName;
	String quizCreator;
	String category;
	Integer maxScore;
	String topper;
	
	
	public String getQuizName() {
		return quizName;
	}
	
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public String getQuizCreator() {
		return quizCreator;
	}
	public void setQuizCreator(String quizCreator) {
		this.quizCreator = quizCreator;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	public String getTopper() {
		return topper;
	}
	public void setTopper(String topper) {
		this.topper = topper;
	}

	public Integer getQuizId() {
		return quizId;
	}

	public void setQuizId(Integer quizId) {
		this.quizId = quizId;
	}
	
}
