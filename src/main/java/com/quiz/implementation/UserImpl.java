package com.quiz.implementation;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.beanutils.BeanUtils;

import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dto.CategoryScoreAndRankingDTO;
import com.quiz.dto.QuizAppException;
import com.quiz.dto.ResultDTO;
import com.quiz.dto.SearchDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Login;
import com.quiz.entities.QuizAttemptTracking;
import com.quiz.entities.User;
import com.quiz.utils.EmailNotification;
import com.quiz.utils.QuizMeUtils;

public class UserImpl {
	
	@Autowired
	IDaoInterfaceForUser usersDao;
	
	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	QuizMeUtils appUtils;
	
	@Autowired
	EmailNotification emailNotification;
	
	@Autowired
	IDaoInterfaceForQuizAttemptTracking quizAttemptTrackingDao;
	
/***********************************************************************************/
	
	public ResponseEntity createUser(UserDTO user)
	
	{
		
		User userObject = new User();
		Login loginObject = new Login();
		try{
			
			BeanUtils.copyProperties(userObject, user);
			Integer userid = appUtils.generateIdValue(0);
			userObject.setUserid(userid);
			
			loginObject.setUserid(userid);
			loginObject.setUsername(user.getEmail());
			loginObject.setPassword(appUtils.passwordEncrypter(user.getNewpassword()));
		
			usersDao.save(userObject);
			loginDao.save(loginObject);
			
			user.setUserid(userid);
			
		}catch (IllegalAccessException e) {
			e.printStackTrace(); 
			return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		catch (InvocationTargetException e) {
			e.printStackTrace(); 
			return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) { 
			e.printStackTrace();
			if(e instanceof QuizAppException)
				return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/*  Email Start  */
		emailNotification.sendEmailonSignUp(user.getEmail(), user.getName());
		/*  Email Complete  */
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		
	}
	
/************************************************************************************/
	
public UserDTO updateUser(UserDTO user, Integer userid)
			
{			
		User userObject = usersDao.getUserById(userid);
		Login loginObject = loginDao.getLoginByUserId(userid);
		
		String oldPassword = appUtils.passwordEncrypter(user.getOldpassword());
						
		if(userObject == null) {return null;}
		if(loginObject == null) {return null;}
		
		if(oldPassword != loginObject.getPassword()){return null; }
		
		if(user.getNewpassword() != null || user.getNewpassword() != "")
		{
			loginObject.setPassword(appUtils.passwordEncrypter(user.getNewpassword()));
			loginDao.update(loginObject);
		}
		
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

public void updateUserQuizAndScoreProfile(int userid, int score, int flag)
{

/*	
 	System.out.println("In user profile - userid => " + userid);
	System.out.println("In user profile - score => " + score);
	System.out.println("In user profile - flag => " + flag);
*/
	User user = usersDao.getUserById(userid);

	switch(flag)
	{
			case 0 : 	
			
			//	System.out.println("Case 0 - Create Quiz " + flag);
				int totalQuizCreated = user.getTotalquizCreated();
				totalQuizCreated = totalQuizCreated + 1;
				user.setTotalquizCreated(totalQuizCreated);
				break;
		
			case 1 : 	
		
			//	System.out.println("Case 1 - Submit Quiz " + flag);
				int totalScore = user.getTotalScore();
				totalScore = totalScore + score;
				user.setTotalScore(totalScore);
		
				int totalQuizTaken = user.getTotalQuizTaken();
				totalQuizTaken = totalQuizTaken + 1;
				user.setTotalQuizTaken(totalQuizTaken);
				break;
	}
	
	usersDao.update(user);
}

/************************************************************************************/

public SearchDTO getGlobalRankingForUser(int userid)
{
	SearchDTO globaRnak = new SearchDTO();
	
	List<User> users = usersDao.getGlobalRanks();
	
	int rank = 0;
	int counter = 0;
	
	for(User user : users)
	{
		counter++;
	/*	System.out.println(counter);
		System.out.println(user.getEmail());
		System.out.println("user => " + user.getUserid());
		System.out.println("cooikie => " + userid);
	*/	
		if(user.getUserid().equals(userid))
		{
		
	/*		System.out.println("Size = > " + users.size());
			System.out.println("Counter => " + counter);
	*/		
			rank = counter;
			
	//		System.out.println("Rank => " + rank);
			break;
		}
	}
	
	globaRnak.setGlobalRank(rank);

	return globaRnak;
}

/************************************************************************************/

public String getUsernameByUserId(int userid)
{
	Login login = loginDao.getLoginByUserId(userid);
	
	if(login == null) { return null; }
	
	String username = login.getUsername();
	
	return username;	
}

/************************************************************************************/

public List<CategoryScoreAndRankingDTO> getCategoryScoreAndRanking(int userid)
{
	List<String> categoriesUserAttempted = getAllCategoriesUserAttempted(userid);
	
	
	if (categoriesUserAttempted == null) //{ return (List<CategoryScoreAndRankingDTO>) new ResponseEntity<List<CategoryScoreAndRankingDTO>>(HttpStatus.OK); }
	{ 
		return null ; 
	}
		
	List<CategoryScoreAndRankingDTO> categoryScoreAndRanking = getUserScoreAndRankingForAllAttemptedCategories(userid,categoriesUserAttempted);
	
	return categoryScoreAndRanking;
	
}


public List<String> getAllCategoriesUserAttempted(int userid)
{
	
	List<QuizAttemptTracking> quizAttempts = quizAttemptTrackingDao.getAllQuizAttemptsForUser(userid);
	 
	if(quizAttempts==null) { return null;}
	
	List<String> listOfCategoriesUserAttempted = new ArrayList<String>();
	
	for(QuizAttemptTracking quizAttempt : quizAttempts)
	{
		if(! listOfCategoriesUserAttempted.contains(quizAttempt.getCategory()))
		{
			listOfCategoriesUserAttempted.add(quizAttempt.getCategory());
		}		
	}

	return listOfCategoriesUserAttempted;
}


public List<CategoryScoreAndRankingDTO>getUserScoreAndRankingForAllAttemptedCategories(int userid, List<String> categoriesUserAttempted)
{

	/* Load all the CategoryScoreAndRankingDTO */
	
	List<CategoryScoreAndRankingDTO> listOfAllCategoryScoreAndRankingEntries = new ArrayList<CategoryScoreAndRankingDTO>();
	
	for(String category : categoriesUserAttempted)
	{
		CategoryScoreAndRankingDTO categoryScoreAndRankingEntry = getCategoryScoreAndRanking(userid, category);
				
		if(categoryScoreAndRankingEntry != null)
		{
			listOfAllCategoryScoreAndRankingEntries.add(categoryScoreAndRankingEntry);
		}
	}
	
	return listOfAllCategoryScoreAndRankingEntries;
}


	public CategoryScoreAndRankingDTO getCategoryScoreAndRanking(int userid, String category)
	{
		
		List <QuizAttemptTracking> rankedQuizAttemptsByCategory = quizAttemptTrackingDao.getAllQuizAttemptsByScoreDescForCategory(category);
		
		
		CategoryScoreAndRankingDTO categoryScoreAndRank = null;
		
		int rank = 0;
		int counter = 0;
		
		for(QuizAttemptTracking quizAttemptTrackingEntry : rankedQuizAttemptsByCategory)
		{
			counter++;
			
			if(quizAttemptTrackingEntry.getUserid().equals(userid))
			{
			
				rank = counter;
				
				categoryScoreAndRank = new CategoryScoreAndRankingDTO();
				
				categoryScoreAndRank.setCategory(category);
				categoryScoreAndRank.setRank(rank);
				categoryScoreAndRank.setScore(quizAttemptTrackingEntry.getScore());
							
				break;
			}
				
		}
		
		return categoryScoreAndRank;
		
	}



}
