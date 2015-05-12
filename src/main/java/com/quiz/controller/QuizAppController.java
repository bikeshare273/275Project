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
import com.quiz.dto.CategoryScoreAndRankingDTO;
import com.quiz.dto.CommentDTO;
import com.quiz.dto.LoginDTO;
import com.quiz.dto.QuizDTO;
import com.quiz.dto.QuizSharingDTO;
import com.quiz.dto.QuizStatDTO;
import com.quiz.dto.QuizSubmitDTO;
import com.quiz.dto.RankingDTO;
import com.quiz.dto.ResultDTO;
import com.quiz.dto.SearchDTO;
import com.quiz.dto.TopperDTO;
import com.quiz.dto.UserDTO;
import com.quiz.entities.Comment;
import com.quiz.entities.Quiz;
import com.quiz.entities.Test;
import com.quiz.entities.User;
import com.quiz.implementation.AttemptedQuizImpl;
import com.quiz.implementation.CommentImpl;
import com.quiz.implementation.GlobalDashboardImpl;
import com.quiz.implementation.QuizImpl;
import com.quiz.implementation.QuizResultsImpl;
import com.quiz.implementation.QuizSharingImpl;
import com.quiz.implementation.SearchImpl;
import com.quiz.implementation.UserCreatedQuizImpl;
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
	QuizResultsImpl quizResultsImpl;

	@Autowired
	AttemptedQuizImpl attemptedQuizImpl;

	@Autowired
	CommentImpl commentImpl;

	@Autowired
	GlobalDashboardImpl globalDashboardImpl;

	@Autowired
	QuizSharingImpl quizSharingImpl;

	@Autowired
	UserCreatedQuizImpl userCreatedQuizImpl;

	@Autowired
	ITestDao testDao;

	/***********************************************************************************************/

	/* Login, Logout and Session Management */

	/***********************************************************************************************/

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getGlobalRank");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/searchQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/createQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/submitQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getAttemptedQuizes");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/postComment");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getAllComments");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getTopScoreCategorywise");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getTopScorer");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getGlobalRank");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getUserCreatedQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/shareQuiz");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getSharedQuizzes");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getCategoryTopScoreAndRank");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/getQuizStats");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/fetchuser");
		registry.addInterceptor(sessionValidatorInterceptor).addPathPatterns("/quizme/updateuser");
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


	@RequestMapping(value = "/logout", method = RequestMethod.GET)
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
	public ResponseEntity updateUser(@Valid @RequestBody UserDTO user, @CookieValue("userid") int userid) {

		return userImpl.updateUser(user, userid);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/users/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getUser(@PathVariable Integer userid) {

		return userImpl.getUser(userid);		
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fetchuser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity fetchUser(@CookieValue("userid") int userid) {

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
		return searchImpl.searchQuiz(userid, searchDTO);		
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

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getQuiz", method = RequestMethod.POST)
	@ResponseBody
	public QuizDTO getQuiz(@RequestBody SearchDTO searchDTO) {

		Integer quizid = Integer.parseInt(searchDTO.getSearchId());

		return quizImpl.getQuiz(quizid);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/submitQuiz", method = RequestMethod.POST)
	@ResponseBody
	public QuizSubmitDTO getQuiz(@CookieValue("userid") int userid, @RequestBody QuizSubmitDTO questionanswers) {

		return quizResultsImpl.evaluatteQuiz(userid, questionanswers);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getAttemptedQuizes", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllQuizAttemptedByUser(@CookieValue("userid") int userid) {
		return attemptedQuizImpl.getAllQuizAttemptsForUser(userid);		
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/postComment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getQuiz(@CookieValue("userid") int userid, @RequestBody CommentDTO commentDTO) {
		System.out.println("comment "+commentDTO.getQuizid()+" "+commentDTO.getComment());
		return commentImpl.createComment(commentDTO, userid, commentDTO.getQuizid());
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getAllComments", method = RequestMethod.POST)
	@ResponseBody
	public List<Comment> getAllCommentsForQuiz(@RequestBody CommentDTO commentDTO) {
		return commentImpl.getAllCommentsForQuiz(commentDTO.getQuizid());		
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getTopScoreCategorywise", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getTopScoreUserForCategory(@RequestBody RankingDTO rankingDTO) {
		return globalDashboardImpl.getTopScoreUserForCategory(rankingDTO);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getTopScorer", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getTopScoreUsers() {
		return globalDashboardImpl.getTopScoreUsers();
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getGlobalRank", method = RequestMethod.GET)
	@ResponseBody
	public SearchDTO getGlobalRankingForUser(@CookieValue("userid") int userid) {

		return userImpl.getGlobalRankingForUser(userid);		
	}










	/***********************************************************************************/

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/getUserCreatedQuiz", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getUserCreatedQuiz(@CookieValue("userid") Integer userid) 
	{
		
		return userCreatedQuizImpl.getUserCreatedQuiz(userid);
	
	}

	/********************************************************************************/








































































	/***********************************************************************************************/	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/shareQuiz", method = RequestMethod.POST)
	@ResponseBody
	public QuizSharingDTO addQuizSharingEntry(@RequestBody QuizSharingDTO quizShareEntry, @CookieValue("userid") int userid) {

		Integer quizid = quizShareEntry.getQuizid();		
		String username = quizShareEntry.getUsername();

		Integer userIdToShare = userImpl.getUserIdByUsername(username);

		if(userIdToShare == null )
		{	
			quizShareEntry.setApplicationMessage("User does not exist !");
			quizShareEntry.setSuccessFlag(false);
			return quizShareEntry; 
		}

		if(username == userImpl.getUsernameByUserId(userid))
		{	
			quizShareEntry.setApplicationMessage("Invalid request. You cannot enter you email id here !");
			quizShareEntry.setSuccessFlag(false);
			return quizShareEntry; 
		}


		quizSharingImpl.addQuizSharingEntry(userIdToShare, quizid, userid);
		quizShareEntry.setApplicationMessage("Recommendation sent to : " + username);
		quizShareEntry.setSuccessFlag(true);

		return quizShareEntry; 

	}

	/***********************************************************************************************/	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getSharedQuizzes", method = RequestMethod.POST)
	@ResponseBody
	public List<Quiz> getSharedQuizzes(@CookieValue("userid") int userid)
	{

		boolean recoFlag = quizSharingImpl.verifyUnAttemptedQuizzesExistForUser(userid);

		if(recoFlag == false ) { return null; }

		List<Quiz> sharedQuizzes = quizSharingImpl.getUnattemptedQuizSharingEntries(userid);

		return sharedQuizzes;
	}

	/***********************************************************************************************/

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getCategoryTopScoreAndRank", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryScoreAndRankingDTO> getCategoryScoreAndRanking(@CookieValue("userid") int userid)
	{	

		return userImpl.getCategoryScoreAndRanking(userid);

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getQuizStats", method = RequestMethod.POST)
	@ResponseBody
	public QuizStatDTO getQuizStats(@RequestBody QuizDTO quizDTO)
	{	

		return quizResultsImpl.getQuizStat(quizDTO);
		
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
