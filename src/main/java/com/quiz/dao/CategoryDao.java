package com.quiz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.quiz.dao.interfaces.IDaoInterfaceForCategory;
import com.quiz.entities.Category;

public class CategoryDao implements IDaoInterfaceForCategory {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public void save(Category category) {

		hibernateTemplate.save(category);
	}

	@Override
	public void update(Category category) {

		hibernateTemplate.update(category);
	}

	@Override
	public void delete(Category category) {

		hibernateTemplate.delete(category);
	}

	@Override
	public Category getCategoryById(Integer categoryid) {
		
		String query = "from Category c where c.categoryid = ?";
		
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) hibernateTemplate.find(query, categoryid);
		
		if(categories.isEmpty()) { return null; }
		
		return categories.get(0);
	}

	@Override
	public Category getCategoryByName(String categoryname) {
		
		String query = "from Category c where lower(c.categoryname) Like lower(?)";
		
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) hibernateTemplate.find(query, "%"+categoryname+"%");
		
		if(categories.isEmpty()) { return null; }
		
		return categories.get(0);
	}

	@Override
	public List<Category> getAllCategories() {
		
		String query = "from Category";
		
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) hibernateTemplate.find(query);
		
		if(categories.isEmpty()) { return null; }
		
		return categories;
	}

}
