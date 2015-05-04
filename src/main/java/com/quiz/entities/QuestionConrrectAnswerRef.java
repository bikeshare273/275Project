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
	
	private Integer questioncorrectanswerid;
	private Integer questionid;
	private Integer optionid;
	
	/***********************************************/
	
	@Id
	@Column(name = "questioncorrectanswerid", unique = true, nullable= false)
	public Integer getQuestioncorrectanswerid() 
	{
		return questioncorrectanswerid;
	}
	
	public void setQuestioncorrectanswerid(Integer questioncorrectanswerid) 
	{
		this.questioncorrectanswerid = questioncorrectanswerid;
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
	
	@Column(name = "optionid", unique = false, nullable= false)
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
