package com.quiz.dto;

public class QuizStatDTO 
{
	QuizDTO quiz;
	Long lowestScore;
	Long highestScore;
	Double averageScore;
	Long totalQuizTakers;
	
	public QuizDTO getQuiz() {
		return quiz;
	}
	public void setQuiz(QuizDTO quiz) {
		this.quiz = quiz;
	}
	public Long getLowestScore() {
		return lowestScore;
	}
	public void setLowestScore(Long lowestScore) {
		this.lowestScore = lowestScore;
	}
	public Long getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(Long highestScore) {
		this.highestScore = highestScore;
	}
	public Double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(Double averageScore) {
		this.averageScore = averageScore;
	}
	public Long getTotalQuizTakers() {
		return totalQuizTakers;
	}
	public void setTotalQuizTakers(Long totalQuizTakers) {
		this.totalQuizTakers = totalQuizTakers;
	}
}
