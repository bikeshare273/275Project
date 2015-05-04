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
public class Quiz  {
	
	
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
	private String category;
	private Integer quizcreator;
	private String quizlevel;
	private Integer popularitycount;
	
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
	
	@Column(name = "category", unique = false, nullable= false)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "quizcreator", unique = false, nullable= false )
	public Integer getQuizcreator() {
		return quizcreator;
	}
	public void setQuizcreator(Integer quizcreator) {
		this.quizcreator = quizcreator;
	}
	
	@Column(name = "quizlevel", unique = false, nullable= false)
	public String getQuizlevel() {
		return quizlevel;
	}
	public void setQuizlevel(String quizlevel) {
		this.quizlevel = quizlevel;
	}
	
	@Column(name = "popularitycount", unique = false, nullable= true)
	public Integer getPopularitycount() {
		return popularitycount;
	}
	public void setPopularitycount(Integer popularitycount) {
		this.popularitycount = popularitycount;
	}
	
}
