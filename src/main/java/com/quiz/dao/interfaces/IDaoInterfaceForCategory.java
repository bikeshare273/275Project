package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.Category;

public interface IDaoInterfaceForCategory {
	
	/*
	 	private Integer categoryid;
		private String categoryname;
		private String categorydescription;
	
	 */
		
	public void save(Category category);
	public void update(Category category);
	public void delete(Category category);
	
	public Category getCategoryById(Integer categoryid);
	public Category getCategoryByName(String categoryname);
	public List<Category> getAllCategories();

}
