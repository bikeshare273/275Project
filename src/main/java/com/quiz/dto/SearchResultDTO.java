package com.quiz.dto;

public class SearchResultDTO {

	private QuizDTO quizDTO;
	private UserDTO userDTO;
	private Long score;
	public QuizDTO getQuizDTO() {
		return quizDTO;
	}
	public void setQuizDTO(QuizDTO quizDTO) {
		this.quizDTO = quizDTO;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	
}
