package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Questions")
public class Question 
{

	/*
	 * 
	 * CREATE TABLE Questions(

		questionid		INTEGER(10),
		quizid			INTEGER(10),			
		questionstring	VARCHAR(200),

		PRIMARY KEY(questionpid));
	 */
	
	/********************************************/
	
	private Integer questionid;
	private Integer quizid;
	private String questionstring;
	
	/*********************************************/
	
	@Id
	@Column(name = "questionid", unique = true, nullable= false)
	public Integer getQuestionid() 
	{
		return questionid;
	}
	public void setQuestionid(Integer questionid) 
	{
		this.questionid = questionid;
	}
	
	@Column(name = "quizid", unique = false, nullable= false)
	public Integer getQuizid() 
	{
		return quizid;
	}
	public void setQuizid(Integer quizid) 
	{
		this.quizid = quizid;
	}
	
	@Column(name = "questionstring", unique = false, nullable= false)
	public String getQuestionstring() {
		return questionstring;
	}
	public void setQuestionstring(String questionstring) {
		this.questionstring = questionstring;
	}
	
	/*****************************************************/
	
}
