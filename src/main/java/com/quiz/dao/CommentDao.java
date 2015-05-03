package com.quiz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForComment;
import com.quiz.entities.Comment;

public class CommentDao implements IDaoInterfaceForComment{

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Comment comment) {

		hibernateTemplate.save(comment);
	}

	@Override
	public void update(Comment comment) {
		
		hibernateTemplate.update(comment);
	}

	@Override
	public void delete(Comment comment) {

		hibernateTemplate.delete(comment);
	}

	@Override
	public Comment getCommentById(Integer commentid) {
		
		String query = "from Comment c where c.commentid = ?";
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) hibernateTemplate.find(query, commentid);
		
		if(comments.isEmpty()) { return null; }
		
		return comments.get(0);
	}

	@Override
	public List<Comment> getAllCommentsForQuiz(Integer quizid) {

		String query = "from Comment c where c.quizid.quizid = ?";
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) hibernateTemplate.find(query, quizid);
		
		if(comments.isEmpty()) { return null; }
		
		return comments;
		
		
	}

	@Override
	public List<Comment> getAllCommentsByUser(Integer userid) {

		String query = "from Comment c where c.userid.userid = ?";
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) hibernateTemplate.find(query, userid);
		
		if(comments.isEmpty()) { return null; }
		
		return comments;
		
	}

}
