package com.quiz.dto;

public class CommentDTO 
{
	
	/**********************************************/
	private Integer commentId;
	private QuizDTO quizDTO;
	private UserDTO userDTO;
	private String comment;
	
	
	/**********************************************/
	public Integer getCommentId() 
	{
		return commentId;
	}
	public void setCommentId(Integer commentId) 
	{
		this.commentId = commentId;
	}
	public QuizDTO getQuizDTO() 
	{
		return quizDTO;
	}
	public void setQuizDTO(QuizDTO quizDTO)
	{
		this.quizDTO = quizDTO;
	}
	public UserDTO getUserDTO() 
	{
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) 
	{
		this.userDTO = userDTO;
	}
	public String getComment() 
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}	
}
