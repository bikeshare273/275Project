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
	private Quiz quizid;
	private String questionstring;
	
	/*********************************************/
	
	@Id
	@Column(name = "questionid", unique = true, nullable= false)
	public Integer getQuestionpid() 
	{
		return questionid;
	}
	public void setQuestionpid(Integer questionpid) 
	{
		this.questionid = questionpid;
	}
	
	@ManyToOne(targetEntity = Quiz.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizid", referencedColumnName = "quizid")
	public Quiz getQuizid() 
	{
		return quizid;
	}
	public void setQuizid(Quiz quizid) 
	{
		this.quizid = quizid;
	}
	
	@Column(name = "questionstring", unique = true, nullable= true)
	public String getQuestionstring() {
		return questionstring;
	}
	public void setQuestionstring(String questionstring) {
		this.questionstring = questionstring;
	}
	
	/*****************************************************/
	
}
