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
public class Options 
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
	private Integer quizid;
	private Integer questionid;
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
	
	
	@ManyToOne(targetEntity = Questions.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "questionid", referencedColumnName = "questionpid")
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
