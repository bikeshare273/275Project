package com.quiz.implementation;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.dto.LoginDTO;
import com.quiz.dto.QuizAppException;
import com.quiz.entities.Login;
import com.quiz.implementation.interfaces.IAuthInterfaceForLogin;
import com.quiz.utils.QuizMeUtils;


public class AuthImplementation implements IAuthInterfaceForLogin{

	@Autowired
	IDaoInterfaceForLogin loginDao;

	@Autowired
	QuizMeUtils quizMeUtils;
	

	@Override
	public ResponseEntity login(LoginDTO loginDTO) {

		String username = loginDTO.getEmail();
		String password = loginDTO.getPassword();
		System.out.println("password-> "+password);
		password = quizMeUtils.passwordEncrypter(password);
		System.out.println("password en -> "+password);
		try{

			Login login = loginDao.getLoginByUserNameAndPassword(username, password);
			
			if(login == null){

			loginDTO.setMessage("Invalid Username/Password");
			loginDTO.setLoginValidationStatus(false);
			throw new QuizAppException(404, "Invalid Username/Password");
			}
	
			else
	
			{
				/* Generate New SessionId */
				
				Integer sessionid = quizMeUtils.generateIdValue(100);
				
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
		if(e instanceof QuizAppException)
			return new ResponseEntity<QuizAppException>((QuizAppException)e, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<QuizAppException>(new QuizAppException(500, "Unexpected Error Encountered"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
	}
}

