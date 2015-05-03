package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Quizzes")
public class Quizzes {
	
	
	/*
	 		CREATE TABLE Quizzes(

			quizid			INTEGER(10),
			quizname		VARCHAR(100),
			quizdescription	VARCHAR(100),
			categoryid		INTEGER(10),
			quizCreatorId	INTEGER(10),
			quizLevelId		INTEGER(10),
			
			PRIMARY KEY(quizid));
	 
	 */

/***************************************************************/
	
	private Integer quizid;
	private String quizname;
	private String quizdescription;
	private Categories categoryid;
	private User quizCreatorId;
	private QuizLevels quizLevelId;
	
/***************************************************************/	
	
	@Id
	@Column(name = "quizid", unique = true, nullable= false)
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
	@Column(name = "quizname", unique = false, nullable= false)
	public String getQuizname() {
		return quizname;
	}
	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}
	
	@Column(name = "quizdescription", unique = false, nullable= false)
	public String getQuizdescription() {
		return quizdescription;
	}
	public void setQuizdescription(String quizdescription) {
		this.quizdescription = quizdescription;
	}
	
	@ManyToOne(targetEntity = Categories.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "categoryid", referencedColumnName = "categoryid")
	public Categories getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Categories categoryid) {
		this.categoryid = categoryid;
	}
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizCreatorId", referencedColumnName = "userid")
	public User getQuizCreatorId() {
		return quizCreatorId;
	}
	public void setQuizCreatorId(User quizCreatorId) {
		this.quizCreatorId = quizCreatorId;
	}
	
	@ManyToOne(targetEntity = QuizLevels.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizLevelId", referencedColumnName = "levelid")
	public QuizLevels getQuizLevelId() {
		return quizLevelId;
	}
	public void setQuizLevelId(QuizLevels quizLevelId) {
		this.quizLevelId = quizLevelId;
	}
	
	
	
}
