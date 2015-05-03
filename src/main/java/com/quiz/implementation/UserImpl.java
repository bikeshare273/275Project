package com.quiz.implementation;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.beanutils.BeanUtils;

import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Login;
import com.quiz.entities.User;
import com.quiz.utils.QuizMeUtils;

public class UserImpl {
	
	@Autowired
	IDaoInterfaceForUser usersDao;
	
	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	QuizMeUtils appUtils;
	
	
/***********************************************************************************/
	
	public UserDTO createUser(UserDTO user)
	
	{
		
		User userObject = new User();
		Login loginObject = new Login();

		try { BeanUtils.copyProperties(userObject, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
		
		Integer userid = appUtils.generateIdValue(0);
		userObject.setUserid(userid);
		
		loginObject.setUserid(userid);
		loginObject.setUsername(user.getEmail());
		loginObject.setPassword(appUtils.passwordEncrypter(user.getPassword()));
	
		usersDao.save(userObject);
		loginDao.save(loginObject);
		
		user.setUserid(userid);
		
		return user;
		
	}
	
/************************************************************************************/
	
public UserDTO updateUser(UserDTO user, Integer userid)
			
{			
		User userObject = usersDao.getUserById(userid);
		
		if(userObject == null) {return null;}
		
		user.setUserid(userObject.getUserid());
		user.setFieldofinterest(userObject.getFieldofinterest());
		user.setTotalquizCreated(userObject.getTotalquizCreated());
		user.setTotalQuizTaken(userObject.getTotalQuizTaken());
		user.setTotalScore(userObject.getTotalScore());
		user.setCredits(userObject.getCredits());
		
		try { BeanUtils.copyProperties(userObject, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
					
		usersDao.update(userObject);
		
		return user;
}
	
/************************************************************************************/
		
public UserDTO getUser(Integer userid)

{
		UserDTO userDTO = new UserDTO();

		User user = usersDao.getUserById(userid);
			
		if(user == null) {return null;}
		
		try { BeanUtils.copyProperties(userDTO, user);} 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (InvocationTargetException e) { e.printStackTrace(); }
				
		return userDTO;
}

/************************************************************************************/

	public List<User> getAllUsers()
	{
		List<User> allUsers = usersDao.getAllUsers();

		if (allUsers == null) {
			return null;
		}

		return allUsers;
	}

	
/************************************************************************************/
	
public boolean deleteUser(Integer userid)

{
		User userObject = usersDao.getUserById(userid);
		Login loginObject = loginDao.getLoginByUserId(userid);
		
		if(userObject == null) {return false;}
		if(loginObject == null) {return false;}
		
		usersDao.delete(userObject);
		loginDao.delete(loginObject);
		
		return true;
}	


/************************************************************************************/

public boolean checkUniqueUsername(String username)

{
		Login login = loginDao.getLoginByUserName(username);
		if(login != null) { System.out.println("Login => " + login.getUsername()); }
		
		if(login == null) {return true;}
		else			 {return false;}

}

/************************************************************************************/
	
public Integer getUserIdByUsername(String username)

{
		Login login = loginDao.getLoginByUserName(username);
				
		if(login == null ) {return null;}
		
		Integer userid = login.getUserid(); 
		
		return userid;
}

/************************************************************************************/

}
