package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="QuizAttemptTracking")
public class QuizAttemptTracking
{
/*
 * CREATE TABLE QuizAttemptTracking(

	quizattemptpid 	INTEGER(10),
	userid			INTEGER(10),
	quizid			INTEGER(10),		
	categoryid		INTEGER(10),
	score			INTEGER(10),

	PRIMARY KEY(quizattemptpid));

 */
	
	
	/************************************/
	private Integer quizattemptpid;
	private Integer userid;
	private Integer quizid;
	private Integer categoryid;
	
	/************************************/
	
	
	@Id
	@Column(name = "quizattemptpid", unique = true, nullable= false)
	public Integer getQuizattemptpid() {
		return quizattemptpid;
	}
	public void setQuizattemptpid(Integer quizattemptpid) {
		this.quizattemptpid = quizattemptpid;
	}
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@ManyToOne(targetEntity = Quizzes.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizid", referencedColumnName = "quizid")
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
	
	@ManyToOne(targetEntity = Categories.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "categoryid", referencedColumnName = "categoryid")
	public Integer getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}
	
	@Column(name = "score", unique = true, nullable= true)
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	private Integer score;
}
