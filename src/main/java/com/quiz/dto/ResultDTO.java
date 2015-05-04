package com.quiz.dto;

/*
 * @author
 * Puneet Popli.
 * 
 * 05/03/2015
 */


public class ResultDTO 
{
	/**************************************************/
	private long score;
	private UserDTO scoreForUser;
	private QuizDTO scoreForQuiz;
	private RankingDTO rankForQuiz;
	
	/**************************************************/
	public long getScore() {
		return score;
	}
	public void setScore(long score) 
	{
		this.score = score;
	}
	public UserDTO getScoreForUser()
	{
		return scoreForUser;
	}
	public void setScoreForUser(UserDTO scoreForUser)
	{
		this.scoreForUser = scoreForUser;
	}
	public QuizDTO getScoreForQuiz()
	{
		return scoreForQuiz;
	}
	public void setScoreForQuiz(QuizDTO scoreForQuiz) 
	{
		this.scoreForQuiz = scoreForQuiz;
	}
	public RankingDTO getRankForQuiz()
	{
		return rankForQuiz;
	}
	public void setRankForQuiz(RankingDTO rankForQuiz)
	{
		this.rankForQuiz = rankForQuiz;
	}
	
	
	
	
	
}
