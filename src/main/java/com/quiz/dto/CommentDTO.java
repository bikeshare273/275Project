package com.quiz.dto;

public class CommentDTO 
{
	
	/**********************************************/
	private Integer commentid;
	private String comment;
	private Integer quizid;
	private Integer userid;
	
	/**********************************************/
	public Integer getCommentid() {
		return commentid;
	}
	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
