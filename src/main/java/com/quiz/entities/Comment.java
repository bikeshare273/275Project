package com.quiz.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Comments")
public class Comment 
{
	
	/*CREATE TABLE Comments(

			commentid	INTEGER(10),
			quizid		INTEGER(10),
			userid		INTEGER(10),
			comment		VARCHAR(200),

			PRIMARY KEY(commentid));*/

	
	/**************************************************/

	private Integer commentid;
	private Quiz quizid;
	private User userid;
	private String comment;
	
	/**************************************************/

	
	@Id
	@Column(name = "commentid", unique = true, nullable= false)
	public Integer getCommentid() {
		return commentid;
	}
	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
	
	@ManyToOne(targetEntity = Quiz.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizid", referencedColumnName = "quizid")
	public Quiz getQuizid() {
		return quizid;
	}
	public void setQuizid(Quiz quizid) {
		this.quizid = quizid;
	}
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	public User getUserid() {
		return userid;
	}
	public void setUserid(User userid) {
		this.userid = userid;
	}
	
	@Column(name = "comment", unique = false, nullable= true)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
