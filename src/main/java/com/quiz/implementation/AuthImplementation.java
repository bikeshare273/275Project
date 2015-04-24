package com.quiz.implementation;



import org.springframework.beans.factory.annotation.Autowired;

import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dto.LoginDTO;
import com.quiz.entities.Login;
import com.quiz.implementation.interfaces.IAuthInterfaceForLogin;
import com.quiz.utils.QuizMeUtils;


public class AuthImplementation implements IAuthInterfaceForLogin{

	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	QuizMeUtils quizMeUtils;
	

	@Override
	public LoginDTO login(LoginDTO loginDTO) {

		String username = loginDTO.getUsername();
		String password = loginDTO.getPassword();
		System.out.println("password-> "+password);
		password = quizMeUtils.passwordEncrypter(password);
		System.out.println("password en -> "+password);
		try{

			Login login = loginDao.getLoginByUserNameAndPassword(username, password);
			
			if(login == null){

			loginDTO.setMessage("Invalid Username/Password");
			loginDTO.setLoginValidationStatus(false);
			}
	
			else
	
			{
				/* Generate New SessionId */
				
				Integer sessionid = quizMeUtils.generateIdValue(0);
				
				login.setSessionid(sessionid);
				
				loginDao.update(login);
				
				//set session id in header
				loginDTO.setSessionId(sessionid);
				loginDTO.setUserid(login.getUserid());
				loginDTO.setMessage("Login Sucessful");
				loginDTO.setLoginValidationStatus(true);
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	return loginDTO;
	}
}

