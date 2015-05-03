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
import com.quiz.dao.LoginDao;
import com.quiz.dao.TestDao;
import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dao.interfaces.ITestDao;
import com.quiz.implementation.AuthImplementation;
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

/********************************************************************************************************************/

											   /* DAO Beans */

/********************************************************************************************************************/

	@Bean
	public IDaoInterfaceForLogin getLoginDao() {
		return new LoginDao();
	}

	@Bean
	public ITestDao getTestDao() {
		return new TestDao();
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
		/*
		 * dataSource.setUrl("jdbc:mysql://localhost:3306/movieapp");
		 * dataSource.setUsername("root"); dataSource.setPassword("");
		 */
		dataSource.setUrl("jdbc:mysql://cmpe.cjatiw41rqvf.us-west-1.rds.amazonaws.com:3306/cmpe");
		dataSource.setUsername("quizmeadmin");
		dataSource.setPassword("quizmeadmin");
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
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource());
		builder.scanPackages("com.quiz.*");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
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
