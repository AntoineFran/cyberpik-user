package com.cda.cyberpik.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class ServiceAspect{
	@Before("within(com.cda.cyberpik.unit.*)")
	public void logBeforeCallingService(JoinPoint joinpoint) {
		String className = joinpoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinpoint.getSignature().getName();
		log.info("Call to " + className + " with the method " + methodName);
	}
	
	@AfterThrowing(pointcut = "within(com.cda.cyberpik.unit.*)", throwing = "ex")
	public void ifServiceThrowsException(JoinPoint joinpoint, Exception ex) {
		String className = joinpoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinpoint.getSignature().getName();
		log.error("Exception on " + className + " with the method " + methodName + ", exception : " + ex.getMessage());
	}
	
}
