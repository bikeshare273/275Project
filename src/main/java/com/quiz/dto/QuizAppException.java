package com.quiz.dto;

import java.util.ArrayList;
import java.util.List;

public class QuizAppException extends Exception {

	private Integer errorCode;
	private String errorMessage;
	
	public QuizAppException(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}

	