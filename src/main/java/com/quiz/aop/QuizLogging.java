package com.quiz.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class QuizLogging {

	
	/*
	@Around("execution(* *.*(..))")
	public void quizAppBasicLogging(ProceedingJoinPoint proceedingJoinPoint){
		try{
			System.out.println("Entering function "+proceedingJoinPoint.getSignature().getName());
			proceedingJoinPoint.proceed();
			System.out.println("Exiting function "+proceedingJoinPoint.getSignature().getName());
		}catch(Throwable e){
			e.printStackTrace();
		}
	}*/
	
}
