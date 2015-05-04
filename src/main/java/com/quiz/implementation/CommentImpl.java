
package com.quiz.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForComment;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.CommentDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Comment;
import com.quiz.entities.Quiz;
import com.quiz.entities.User;
import com.quiz.utils.QuizMeUtils;

public class CommentImpl 
{
	@Autowired
	IDaoInterfaceForUser userDao;
	
	@Autowired
	IDaoInterfaceForComment commentDao;
	
	@Autowired
	IDaoInterfaceForQuiz quizDao;
	
	@Autowired
	QuizMeUtils appUtils;
	
	
	/*
	 * 1. Create comments
	 */
	
	public ResponseEntity createComment(CommentDTO commentDTO, Integer userId, Integer quizId)
	{
		Comment commentObject = new Comment();
		
		BeanUtils.copyProperties(commentObject, commentDTO);
		Integer commentId = appUtils.generateIdValue(0);
		String commentDescription = commentDTO.getComment();
		
		commentObject.setCommentid(commentId);
		commentObject.setComment(commentDescription);
	
		User userObject = userDao.getUserById(userId);
		Quiz quizObject = quizDao.getQuizById(quizId);
		
		commentObject.setUserid(userObject.getUserid());
		commentObject.setQuizid(quizObject.getQuizid());
		
		commentDao.save(commentObject);
		
		return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
		
	}
	
	/*
	 * 
	 * 2. Get all comments for quiz
	 */
	
	public List<Comment> getAllCommentsForQuiz(Integer quizId)
	{
		List<Comment> allComments = commentDao.getAllCommentsForQuiz(quizId);
		
		if(allComments==null)
		{
			return null;
		}
		
		return allComments;
		
	}

}
