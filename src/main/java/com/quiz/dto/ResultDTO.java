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
	private Integer score;
	private UserDTO scoreForUser;
	private QuizDTO scoreForQuiz;
	private RankingDTO rankForQuiz;
	private Long rank;
	
	/**************************************************/
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) 
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
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	
	
	
	
	
}
