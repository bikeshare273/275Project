package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "QuizLevels")
public class QuizLevel {

	/*
			CREATE TABLE QuizLevels (

			levelname			VARCHAR(100),
			leveldescription	VARCHAR(200),

			PRIMARY KEY (levelid));

	 */

/**************************************************************************************/	

	private String 	levelname;
	private String 	leveldescription;	

/**************************************************************************************/	
	
	@Id
	@Column(name = "levelname", unique = false, nullable= false)
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	@Column(name = "leveldescription", unique = false, nullable= true)
	public String getLeveldescription() {
		return leveldescription;
	}
	public void setLeveldescription(String leveldescription) {
		this.leveldescription = leveldescription;
	}

/**************************************************************************************/

}
