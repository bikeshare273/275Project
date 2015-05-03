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
public class Questions 
{

	/*
	 * 
	 * CREATE TABLE Questions(

		questionpid		INTEGER(10),
		quizid			INTEGER(10),			
		questionid		VARCHAR(200),

		PRIMARY KEY(questionpid));
	 */
	
	/********************************************/
	
	private Integer questionpid;
	private Integer quizid;
	private String questionid;
	
	
	
	/*********************************************/

	
	
	@Id
	@Column(name = "questionpid", unique = true, nullable= false)
	public Integer getQuestionpid() 
	{
		return questionpid;
	}
	public void setQuestionpid(Integer questionpid) 
	{
		this.questionpid = questionpid;
	}
	
	@ManyToOne(targetEntity = Quizzes.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizid", referencedColumnName = "quizid")
	public Integer getQuizid() 
	{
		return quizid;
	}
	public void setQuizid(Integer quizid) 
	{
		this.quizid = quizid;
	}
	
	@Column(name = "questionid", unique = true, nullable= true)
	public String getQuestionid()
	{
		return questionid;
	}
	
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	
	
	/*****************************************************/
	
}
