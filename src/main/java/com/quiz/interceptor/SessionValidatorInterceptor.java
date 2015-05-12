package com.quiz.interceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.quiz.dao.interfaces.IDaoInterfaceForLogin;
import com.quiz.entities.Login;
import com.quiz.utils.QuizMeUtils;


public class SessionValidatorInterceptor implements HandlerInterceptor{

	@Autowired
	IDaoInterfaceForLogin loginDao;
	
	@Autowired
	QuizMeUtils quizMeUtils;
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		//retrieve cookie here
		boolean loggedIn = false;
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			Map<String,String> coockieMap = quizMeUtils.getCookiesValue(cookies);
			String username = coockieMap.get("username");
			String sessionid = coockieMap.get("sessionid");
			System.out.println("username "+username);
			System.out.println("sessionid "+sessionid);
			if(username == null || "".equals(username.trim()) || sessionid == null || "".equals(sessionid.trim())){
				res.sendError(400, "invalid session");
				return false;
			}
			Login login = loginDao.getLoginByUserNameAndSessionId(username, Integer.parseInt(sessionid));
			
			if(login != null) loggedIn = true;
			else{
				res.sendError(400, "invalid session");
			}
			
			System.out.println("loggedin "+loggedIn);
		
		}
		return loggedIn;
	}

}