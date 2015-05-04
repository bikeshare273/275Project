package com.quiz.dto;

/*
 * @author
 * Puneet Popli.
 * 
 * 05/03/2015
 */

public class RankingDTO 
{
	
	/**************************************************/

	private Long rank;
	private Integer score;
	private String category;
	
	/**************************************************/
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
