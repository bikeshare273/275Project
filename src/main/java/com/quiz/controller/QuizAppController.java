package com.quiz.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.quiz.configuration.QuizMeConfiguration;
import com.quiz.dao.interfaces.ITestDao;
import com.quiz.dto.LoginDTO;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.SearchDTO;
import com.quiz.dto.SearchResultDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Test;
import com.quiz.entities.User;
import com.quiz.implementation.QuizImpl;
import com.quiz.implementation.SearchImpl;
import com.quiz.implementation.UserImpl;
import com.quiz.implementation.interfaces.IAuthInterfaceForLogin;
import com.quiz.interceptor.SessionValidatorInterceptor;



@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/quizme/*")
@Import(QuizMeConfiguration.class)
public class QuizAppController extends WebMvcConfigurerAdapter {	

/***********************************************************************************************/
		
						     /*	Spring - Beans Auto-Wiring */

/***********************************************************************************************/

	@Autowired
	IAuthInterfaceForLogin authInterfaceForLogin;

	@Autowired
	SessionValidatorInterceptor sessionValidatorInterceptor;
	
	@Autowired
	UserImpl userImpl;
	
	@Autowired
	SearchImpl searchImpl;
	
	@Autowired
	QuizImpl quizImpl;
		
	@Autowired
	ITestDao testDao;
	
/***********************************************************************************************/

						/* Login, Logout and Session Management */

/***********************************************************************************************/

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/loggedin");
	}

	@RequestMapping(value="/loggedin", method = RequestMethod.GET)
	@ResponseBody
	private boolean logeedin() {
	return true;
	}

	@RequestMapping("/login")
	@ResponseBody
	private ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
		ResponseEntity responseEntity = authInterfaceForLogin.login(loginDTO);
		if(responseEntity.getStatusCode() == HttpStatus.OK){
			response.addCookie(new Cookie("sessionid", Integer.toString(loginDTO.getSessionId())));
			response.addCookie(new Cookie("username", loginDTO.getEmail()));
			response.addCookie(new Cookie("userid", Integer.toString(loginDTO.getUserid())));
		}
		return responseEntity;
	}

	
	@RequestMapping("/logout")
	@ResponseBody
	private boolean logout(HttpServletResponse response) {

		response.addCookie(new Cookie("sessionid", ""));
		response.addCookie(new Cookie("username", ""));
		response.addCookie(new Cookie("userid", ""));

		return true;
	}

	/* Required URI => http://localhost:8082/quizme/verifyUniqueUsername?email=z@gmail.com*/
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/verifyUniqueUsername", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUniqueUsername(@RequestParam ("email") String username) {
				
		return userImpl.checkUniqueUsername(username);
		
	}
	

/***********************************************************************************************/

									/* User Entity APIs */

/***********************************************************************************************/
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity createUser(@Valid @RequestBody UserDTO user) {
		
		return userImpl.createUser(user);
	}


	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	@ResponseBody
	public UserDTO updateUser(@Valid @RequestBody UserDTO user, @CookieValue("userid") int userid) {

		return userImpl.updateUser(user, userid);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/users/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getUser(@PathVariable Integer userid) {

		return userImpl.getUser(userid);		
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fetchuser", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO fetchUser(@CookieValue("userid") int userid) {

		return userImpl.getUser(userid);		

	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fetchallusers", method = RequestMethod.POST)
	@ResponseBody
	public List<User> fetchAllUsers() {

		return userImpl.getAllUsers();		

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	@ResponseBody
	public void deleteUser(@RequestBody SearchDTO searchDTO) {

		Integer userid = Integer.parseInt(searchDTO.getSerachString());
		
		userImpl.deleteUser(userid);
	}

	
	//swapnil
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchQuiz", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity searchQuiz(@CookieValue("userid") int userid, @RequestBody SearchDTO searchDTO) {
		return searchImpl.searchQuiz(searchDTO);		
	}
	
	//swapnil

/***********************************************************************************************/

								  /* Application REST APIs  */

/***********************************************************************************************/
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
	@ResponseBody
	public QuizDTO createQuiz(@CookieValue("userid") int userid, @RequestBody QuizDTO quizDTO) {
						
		return quizImpl.createQuiz(quizDTO, userid);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/***********************************************************************************************/	
	
									/* Test API */ 
	
/***********************************************************************************************/
	
	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Test dbtest(@PathVariable int id) {

		Test test = new Test();

		DateTime dt = new DateTime();

		test.setTestIdString(dt.toString());
		test.setTestString("Last loaded record => " + id);
		testDao.saveTest(test);
		Test savedTest = testDao.getTest(test);

		return savedTest;
	}
	
/***********************************************************************************************/
	
}
