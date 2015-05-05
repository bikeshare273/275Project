package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	
/***************************************************************************************/

	private Integer commentid;
	private String comment;
	private Integer quizid;
	private Integer userid;
	
/***************************************************************************************/
	
	@Id
	@Column(name = "commentid", unique = true, nullable= false)
	public Integer getCommentid() {
		return commentid;
	}
	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
	
	@Column(name = "comment", unique = false, nullable= true)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Column(name = "quizid", unique = false, nullable= true)
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	
	@Column(name = "userid", unique = false, nullable= true)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
/***************************************************************************************/
}
