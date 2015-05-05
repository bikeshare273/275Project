package com.quiz.dto;

public class SearchDTO {
	
	private String serachString;
	private String searchId;
	private Integer globalRank;

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSerachString() {
		return serachString;
	}

	public void setSerachString(String serachString) {
		this.serachString = serachString;
	}

	public int getGlobalRank() {
		return globalRank;
	}

	public void setGlobalRank(int globalRank) {
		this.globalRank = globalRank;
	}

}

