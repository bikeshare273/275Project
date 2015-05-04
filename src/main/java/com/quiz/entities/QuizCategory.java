package com.quiz.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QuizCategories")
public class QuizCategory {

	private String category;
	private String categorydescription;

	@Id
	@Column(name = "category", unique = true, nullable = false)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "categorydescription", unique = false, nullable = true)
	public String getCategorydescription() {
		return categorydescription;
	}

	public void setCategorydescription(String categorydescription) {
		this.categorydescription = categorydescription;
	}

}
