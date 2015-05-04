package com.quiz.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForQuizSharing;
import com.quiz.entities.QuizSharing;

@Transactional
public class QuizSharingDao implements IDaoInterfaceForQuizSharing {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public void save(QuizSharing quizSharingEntry) {

		hibernateTemplate.save(quizSharingEntry);
	}

	@Override
	public void update(QuizSharing quizSharingEntry) {

		hibernateTemplate.update(quizSharingEntry);
	}

	@Override
	public void delete(QuizSharing quizSharingEntry) {

		hibernateTemplate.delete(quizSharingEntry);
	}

	@Override
	public QuizSharing getQuizSharingById(Integer quizSharingid) {

		String query = "from QuizSharing q where q.quizsharingid = ?";

		@SuppressWarnings("unchecked")
		List<QuizSharing> quizsharingentries = (List<QuizSharing>) hibernateTemplate.find(query, quizSharingid);

		if (quizsharingentries.isEmpty()) {
			return null;
		}

		return quizsharingentries.get(0);

	}

	@Override
	public List<QuizSharing> getQuizSharingByUserId(Integer userid) {

		String query = "from QuizSharing q where q.userid = ?";

		@SuppressWarnings("unchecked")
		List<QuizSharing> quizsharingentries = (List<QuizSharing>) hibernateTemplate.find(query, userid);

		if (quizsharingentries.isEmpty()) {
			return null;
		}

		return quizsharingentries;
	}

	@Override
	public List<QuizSharing> getUnttemptedQuizSharings(Integer userid) {

		String query = "from QuizSharing q where q.userid = ? and q.completedflag is false ";

		@SuppressWarnings("unchecked")
		List<QuizSharing> quizsharingentries = (List<QuizSharing>) hibernateTemplate.find(query, userid);

		if (quizsharingentries.isEmpty()) {
			return null;
		}

		return quizsharingentries;
	}

	@Override
	public List<QuizSharing> getAllQuizSharingEntriesByRecommender(	Integer recommenderid) {

		String query = "from QuizSharing q where q.recommenderid = ?";

		@SuppressWarnings("unchecked")
		List<QuizSharing> quizsharingentries = (List<QuizSharing>) hibernateTemplate.find(query, recommenderid);

		if (quizsharingentries.isEmpty()) {
			return null;
		}

		return quizsharingentries;
	}

	@Override
	public QuizSharing getQuizSharingByUserIdAndQuizId(Integer userid, Integer quizid) {
		String query = "from QuizSharing q where q.userid = ? and q.quiz = ?";
		@SuppressWarnings("unchecked")
		List<QuizSharing> quizSharingEntries = (List<QuizSharing>) hibernateTemplate.find(query, userid, quizid);

		if (quizSharingEntries.isEmpty()) {
			return null;
		} else {
			return quizSharingEntries.get(0);
		}
	}

	@Override
	public List<QuizSharing> getQuizSharingByQuizId(Integer quizid) {

		String query = "from QuizSharing q where q.quiz = ?";
		@SuppressWarnings("unchecked")
		List<QuizSharing> quizSharingEntries = (List<QuizSharing>) hibernateTemplate
				.find(query, quizid);

		if (quizSharingEntries.isEmpty()) {
			return null;
		} else {
			return quizSharingEntries;
		}

	}

	@Override
	public List<QuizSharing> getQuizSharingByUserIdAndQuizIdAndRecommenderId(Integer userid, Integer quizid, Integer recommenderid) {

		String query = "from QuizSharing q where q.userid = ? and q.quiz = ? and recommenderid = ?";
		@SuppressWarnings("unchecked")
		List<QuizSharing> quizSharingEntries = (List<QuizSharing>) hibernateTemplate
				.find(query, userid, quizid, recommenderid);

		if (quizSharingEntries.isEmpty()) {
			return null;
		} else {
			return quizSharingEntries;
		}
	}

}
