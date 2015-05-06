package com.quiz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class QuizLogging {

	@Before("execution(* com.quiz.implementation.*.*(..))")
	public void quizAppBasicBeforeLogging(JoinPoint joinPoint){
		try{
			System.out.println("Entering function "+joinPoint.getSignature().getName());
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	
	@After("execution(* com.quiz.implementation.*.*(..))")
	public void quizAppBasicAfterLogging(JoinPoint joinPoint){
		try{
			System.out.println("Exiting function "+joinPoint.getSignature().getName());
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}