package com.quiz.dao.interfaces;

import com.quiz.entities.Test;

public interface ITestDao {

	public void saveTest(Test test);
	
	public Test getTest(Test test);
	
}
