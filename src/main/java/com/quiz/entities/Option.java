package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	private Integer optionid;
	private Integer quizid;
	private Integer questionid;
	private String optionvalue;

	/*****************************************************/
	
	
	@Id
	@Column(name = "optionid", unique = true, nullable= false)
	public Integer getOptionid() 
	{
		return optionid;
	}
	
	public void setOptionid(Integer optionid)
	{
		this.optionid = optionid;
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
	
	@Column(name = "questionid", unique = false, nullable= false)
	public Integer getQuestionid() 
	{
		return questionid;
	}
	
	public void setQuestionid(Integer questionid)
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
