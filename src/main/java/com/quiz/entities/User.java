package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Users")
public class User {

	/*
	 		CREATE TABLE Users (

				userid 				INTEGER(10),
				email				VARCHAR(100),
				name				VARCHAR(100),
				phonenumber			VARCHAR(20),
				address				VARCHAR(100),
				country				VARCHAR(20),
				fieldofinterest		VARCHAR(100),
				credits				INTEGER(5),
				age					Integer(3),
				state				VARCHAR(20),
				totalScore			INTEGER(5),
				totalQuizTaken		INTEGER(5),
				totalquizCreated	INTEGER(5),

			PRIMARY KEY(userid));
	
	 */
	
/*********************************************************************/	
	
	private Integer userid;
	private String email;
	
	private String name;
	private String phonenumber;
	private String country;
	private String address;
	private String state;
	private Integer age;
	
	private String fieldofinterest;
	private Integer credits;
	private Integer totalScore;
	private Integer totalQuizTaken;
	private Integer totalquizCreated;
	
/*********************************************************************/	
	
	@Id
	@Column(name = "userid", unique = true, nullable = false)	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "name", unique = false, nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "address", unique = false, nullable = true)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "phonenumber", unique = false, nullable = true)
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Column(name = "country", unique = false, nullable = true)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "fieldofinterest", unique = false, nullable = true)
	public String getFieldofinterest() {
		return fieldofinterest;
	}
	public void setFieldofinterest(String fieldofinterest) {
		this.fieldofinterest = fieldofinterest;
	}
	
	@Column(name = "credits", unique = false, nullable = true)
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	
	@Column(name = "age", unique = false, nullable = true)
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name = "state", unique = false, nullable = true)
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "totalScore", unique = false, nullable = true)
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	@Column(name = "totalQuizTaken", unique = false, nullable = true)
	public Integer getTotalQuizTaken() {
		return totalQuizTaken;
	}
	public void setTotalQuizTaken(Integer totalQuizTaken) {
		this.totalQuizTaken = totalQuizTaken;
	}
	
	@Column(name = "totalquizCreated", unique = false, nullable = true)
	public Integer getTotalquizCreated() {
		return totalquizCreated;
	}
	public void setTotalquizCreated(Integer totalquizCreated) {
		this.totalquizCreated = totalquizCreated;
	}
	
}
