package com.quiz.dao.interfaces;


import java.util.List;

import com.quiz.entities.Comment;

public interface IDaoInterfaceForComment {
	
/*
  	private Integer commentid;
	private Quiz quizid;
	private User userid;
	private String comment;
 */
	
	public void save(Comment comment);
	public void update(Comment comment);
	public void delete(Comment comment);
	
	public Comment getCommentById(Integer commentid);
	
	//we require this
	public List<Comment> getAllCommentsForQuiz(Integer quizid);
	public List<Comment> getAllCommentsByUser(Integer userid);
	
}
