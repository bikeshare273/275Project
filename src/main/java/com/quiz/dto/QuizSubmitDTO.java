package com.quiz.dto;

import java.util.List;

public class QuizSubmitDTO {
	
	private Integer quizid;
	private List<QuestionAnswerDTO> setOfQuestionAndAnswers;
	private Integer totalScoreForquiz;
	private Integer categoryRank;
	private Integer globalRank;
		
	public List<QuestionAnswerDTO> getSetOfQuestionAndAnswers() {
		return setOfQuestionAndAnswers;
	}

	public void setSetOfQuestionAndAnswers(List<QuestionAnswerDTO> setOfQuestionAndAnswers) {
		this.setOfQuestionAndAnswers = setOfQuestionAndAnswers;
	}

	public Integer getTotalScoreForquiz() {
		return totalScoreForquiz;
	}

	public void setTotalScoreForquiz(Integer totalScoreforquiz) {
		this.totalScoreForquiz = totalScoreforquiz;
	}

	public Integer getCategoryRank() {
		return categoryRank;
	}

	public void setCategoryRank(Integer categoryRank) {
		this.categoryRank = categoryRank;
	}

	public Integer getGlobalRank() {
		return globalRank;
	}

	public void setGlobalRank(Integer globalRank) {
		this.globalRank = globalRank;
	}

	public Integer getQuizid() {
		return quizid;
	}

	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
}
