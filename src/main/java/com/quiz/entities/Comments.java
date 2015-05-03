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
public class Comments 
{
	
	/*CREATE TABLE Comments(

			commentid	INTEGER(10),
			quizid		INTEGER(10),
			userid		INTEGER(10),
			comment		VARCHAR(200),

			PRIMARY KEY(commentid));*/

	
	/**************************************************/

	private Integer commentid;
	private Integer quizid;
	private Integer userid;
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
	
	@ManyToOne(targetEntity = Quizzes.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quizid", referencedColumnName = "quizid")
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "comment", unique = true, nullable= true)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
}
