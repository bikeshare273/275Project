package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="QuestionConrrectAnswerRef")
public class QuestionConrrectAnswerRef 
{

	/*
	 * 
	 * CREATE TABLE QuestionConrrectAnswerRef(

		questioncorrectanswerpid	INTEGER(10),	
		questionid					INTEGER(10),	
		optionid					INTEGER(10),

		PRIMARY KEY(questioncorrectanswerpid));
	 * 
	 */
	
	private Integer questioncorrectanswerpid;
	private Integer questionid;
	private Integer optionid;
	
	/***********************************************/
	
	@Id
	@Column(name = "questioncorrectanswerpid", unique = true, nullable= false)
	public Integer getQuestioncorrectanswerpid() 
	{
		return questioncorrectanswerpid;
	}
	
	public void setQuestioncorrectanswerpid(Integer questioncorrectanswerpid) 
	{
		this.questioncorrectanswerpid = questioncorrectanswerpid;
	}
	
	@ManyToOne(targetEntity = Questions.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "questionid", referencedColumnName = "questionid")
	public Integer getQuestionid() 
	{
		return questionid;
	}
	
	public void setQuestionid(Integer questionid) 
	{
		this.questionid = questionid;
	}
	
	@OneToOne(targetEntity = Options.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "optionid", referencedColumnName = "optionpid")
	public Integer getOptionid() 
	{
		return optionid;
	}
	
	public void setOptionid(Integer optionid) 
	{
		this.optionid = optionid;
	}
	
	
	/***********************************************/
}
