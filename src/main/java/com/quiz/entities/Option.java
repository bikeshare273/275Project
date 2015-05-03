package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Options")
public class Option 
{

	/*
	 * 
	 * 
	 * CREATE TABLE Options(

		optionpid			INTEGER(10),
		quizid				INTEGER(10),
		questionid			INTEGER(10),
		optionvalue			VARCHAR(100),

		PRIMARY KEY(optionpid));
	 */
	
	
	/*****************************************************/
	
	private Integer optionpid;
	private Quiz quizid;
	private Question questionid;
	private String optionvalue;

	/*****************************************************/
	
	
	@Id
	@Column(name = "optionpid", unique = true, nullable= false)
	public Integer getOptionpid() 
	{
		return optionpid;
	}
	
	public void setOptionpid(Integer optionpid)
	{
		this.optionpid = optionpid;
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
	
	
	@ManyToOne(targetEntity = Question.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "questionid", referencedColumnName = "questionpid")
	public Question getQuestionid() 
	{
		return questionid;
	}
	
	public void setQuestionid(Question questionid)
	{
		this.questionid = questionid;
	}
	
	@Column(name = "optionvalue", unique = false, nullable= true)
	public String getOptionvalue()
	{
		return optionvalue;
	}
	
	public void setOptionvalue(String optionvalue) 
	{
		this.optionvalue = optionvalue;
	}
	
	
	
	
	
	/*****************************************************/
}
