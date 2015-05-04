package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QuizAttemptTracking")
public class QuizAttemptTracking
{
/*
 * CREATE TABLE QuizAttemptTracking(

	quizattemptid 	INTEGER(10),
	userid			INTEGER(10),
	quizid			INTEGER(10),		
	categoryid		INTEGER(10),
	score			INTEGER(10),

	PRIMARY KEY(quizattemptpid));

 */
	
	
	/************************************/
	private Integer quizattemptid;
	private Integer userid;
	private Integer quizid;
	private String  category;
	private Integer score;
	
	
	/************************************/
	
	
	@Id
	@Column(name = "quizattemptid", unique = true, nullable= false)
	public Integer getQuizattemptid() {
		return quizattemptid;
	}
	public void setQuizattemptid(Integer quizattemptid) {
		this.quizattemptid = quizattemptid;
	}
	
	@Column(name = "userid", unique = true, nullable= false)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "quizid", unique = true, nullable= false)
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
	@Column(name = "category", unique = true, nullable= false)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "score", unique = true, nullable= false)
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	
	
}
