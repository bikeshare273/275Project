package com.quiz.dao.interfaces;

import java.util.List;

import com.quiz.entities.User;

public interface IDaoInterfaceForUser {

	public void save(User user);
	public void update(User user);
	public void delete(User user);
	
	public User getUserById(Integer userid);
	public List<User> getUserByName(String name);
	public User getUserByIdAndUserName(Integer userid, String username);
	public List<User> getAllUsers();
	public List<User> getGlobalRanks();

}