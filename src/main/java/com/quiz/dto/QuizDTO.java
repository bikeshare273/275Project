package com.quiz.dto;

import java.util.List;

import com.quiz.entities.Category;
import com.quiz.entities.User;

public class QuizDTO {

	private Integer quizid;
	private String quizname;
	private String quizdescription;
	private String categoryid;
	private Category categoryObject;
	private User quizcreator;
	private String quizlevel;
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
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public User getQuizcreator() {
		return quizcreator;
	}
	public void setQuizcreator(User quizcreator) {
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
	public Category getCategoryObject() {
		return categoryObject;
	}
	public void setCategoryObject(Category categoryObject) {
		this.categoryObject = categoryObject;
	}
}
