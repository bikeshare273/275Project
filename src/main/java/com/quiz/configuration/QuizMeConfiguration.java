package com.quiz.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;

import com.quiz.dao.CommentDao;
import com.quiz.dao.LoginDao;
import com.quiz.dao.OptionDao;
import com.quiz.dao.QuestionCorrectAnswerDao;
import com.quiz.dao.QuestionDao;
import com.quiz.dao.QuizAttemptTrackingDao;
import com.quiz.dao.QuizDao;
import com.quiz.dao.QuizSharingDao;
import com.quiz.dao.TestDao;
import com.quiz.dao.UserDao;
import com.quiz.dao.interfaces.IDaoInterfaceForComment;
import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dao.interfaces.IDaoInterfaceForOption;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestion;
import com.quiz.dao.interfaces.IDaoInterfaceForQuestionCorrectAnswer;
import com.quiz.dao.interfaces.IDaoInterfaceForQuiz;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizAttemptTracking;
import com.quiz.dao.interfaces.IDaoInterfaceForQuizSharing;
import com.quiz.dao.interfaces.IDaoInterfaceForUser;
import com.quiz.dao.interfaces.ITestDao;
import com.quiz.implementation.AttemptedQuizImpl;
import com.quiz.implementation.AuthImplementation;
import com.quiz.implementation.CommentImpl;
import com.quiz.implementation.QuizImpl;
import com.quiz.implementation.QuizResultsImpl;
import com.quiz.implementation.SearchImpl;
import com.quiz.implementation.UserImpl;
import com.quiz.implementation.interfaces.IAuthInterfaceForLogin;
import com.quiz.interceptor.SessionValidatorInterceptor;
import com.quiz.utils.QuizMeUtils;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class QuizMeConfiguration {

/********************************************************************************************************************/

										  /* Implementation Beans */
	
/********************************************************************************************************************/

	@Bean
	public UserImpl getUserImpl(){
		return new UserImpl();
	}
	
	@Bean
	public SearchImpl getSearchImpl(){
		return new SearchImpl();
	}
	
	@Bean
	public QuizImpl getQuizImpl(){
		return new QuizImpl();
	}
	
	@Bean
	public QuizResultsImpl getQuizResultsImpl(){
		return new QuizResultsImpl();
	}
	
	@Bean
	public AttemptedQuizImpl getAttemptedQuizImpl(){
		return new AttemptedQuizImpl();
	}
	
	@Bean
	public CommentImpl getCommentImpl(){
		return new CommentImpl();
	}
	
	
/********************************************************************************************************************/

											   /* DAO Beans */

/********************************************************************************************************************/

	@Bean
	public IDaoInterfaceForUser getUserDao(){
		return new UserDao();
	}
	
	@Bean
	public IDaoInterfaceForLogin getLoginDao() {
		return new LoginDao();
	}
	
	@Bean
	public ITestDao getTestDao() {
		return new TestDao();
	}
	
	@Bean
	public IDaoInterfaceForComment getCommentsDao(){
		return new CommentDao();
	}
	
	@Bean
	public IDaoInterfaceForOption getOptionsDao(){
		return new OptionDao();
	}
	
	@Bean
	public IDaoInterfaceForQuestion getQuestionsDao(){
		return new QuestionDao();
	}
	
	@Bean
	public IDaoInterfaceForQuestionCorrectAnswer getQuestionCorrectAnswerDao(){
		return new QuestionCorrectAnswerDao();
	}
	
	@Bean
	public IDaoInterfaceForQuiz getQuizDao(){
		return new QuizDao();
	}
	
	@Bean
	public IDaoInterfaceForQuizAttemptTracking getQuizAttemptTrackingDao(){
		return new QuizAttemptTrackingDao();
	}
	
	@Bean
	public IDaoInterfaceForQuizSharing getQuizSharingDao(){
		return new QuizSharingDao();
	}
	
	
/********************************************************************************************************************/

										/* Intercepter and Util Beans */

/********************************************************************************************************************/

	@Bean
	public SessionValidatorInterceptor sessionValidatorInterceptor() {
		return new SessionValidatorInterceptor();
	}

	@Bean
	public IAuthInterfaceForLogin getAuthInterfaceForLogin() {
		return new AuthImplementation();
	}

	@Bean
	public QuizMeUtils getQuizMeUtils() {
		return new QuizMeUtils();
	}

/********************************************************************************************************************/

										/* Configuration Beans */

/********************************************************************************************************************/

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://cmpe.cjatiw41rqvf.us-west-1.rds.amazonaws.com:3306/quizme");
		dataSource.setUsername("cmpeadmin");
		dataSource.setPassword("cmpeadmin");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(5);
		return dataSource;
	}

/********************************************************************************************************************/

	/**
	 * @return HibernateTemplate() This is bean creation method for
	 *         HibernateTemplate.
	 */
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());

	}

/********************************************************************************************************************/

	/**
	 * @return SessionFactory() This is bean creation method for SessionFactory.
	 */
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		builder.scanPackages("com.quiz.*");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		builder.addProperties(hibernateProperties);
		return builder.buildSessionFactory();
	}

/********************************************************************************************************************/

	/**
	 * @return HibernateTransactionManager() This is bean creation method for
	 *         HibernateTransactionManager.
	 */
	@Bean
	@Primary
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}

/********************************************************************************************************************/

}
