package com.quiz.dto;

import java.util.List;

import com.quiz.entities.User;

public class QuizDTO {

	private Integer quizid;
	private String quizname;
	private String quizdescription;
	private String category;
	private String quizlevel;
	private Integer quizcreator;
	private Integer popularitycount;
	private List<QuestionDTO> questions;
	
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	public String getQuizname() {
		return quizname;
	}
	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}
	public String getQuizdescription() {
		return quizdescription;
	}
	public void setQuizdescription(String quizdescription) {
		this.quizdescription = quizdescription;
	}
	public Integer getQuizcreator() {
		return quizcreator;
	}
	public void setQuizcreator(Integer quizcreator) {
		this.quizcreator = quizcreator;
	}
	public String getQuizlevel() {
		return quizlevel;
	}
	public void setQuizlevel(String quizlevel) {
		this.quizlevel = quizlevel;
	}
	public Integer getPopularitycount() {
		return popularitycount;
	}
	public void setPopularitycount(Integer popularitycount) {
		this.popularitycount = popularitycount;
	}
	public List<QuestionDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
