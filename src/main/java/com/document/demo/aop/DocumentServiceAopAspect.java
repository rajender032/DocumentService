package com.document.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DocumentServiceAopAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before("execution(* com.document.demo.*.*.*(..))")
	public void before(JoinPoint joinPoint) {
		String packageName = joinPoint.getSignature().getDeclaringTypeName();
	    String methodName = joinPoint.getSignature().getName();
	    
		logger.info(packageName+"."+methodName+"() method start");
	}
	
	@After("execution(* com.document.demo.*.*.*(..))")
	public void after(JoinPoint joinPoint) {
		String packageName = joinPoint.getSignature().getDeclaringTypeName();
	    String methodName = joinPoint.getSignature().getName();
	    
		logger.info(packageName+"."+methodName+"() method end");
	}
	
	@Around("@annotation(com.document.demo.aop.TrackTime)")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		String packageName = joinPoint.getSignature().getDeclaringTypeName();
	    String methodName = joinPoint.getSignature().getName();
		long startTime = System.currentTimeMillis();
		
		joinPoint.proceed();
		
		long timeTaken = System.currentTimeMillis()-startTime;
		logger.info("Time Taken by {} is {}", packageName+"."+methodName, timeTaken);
	}
}
