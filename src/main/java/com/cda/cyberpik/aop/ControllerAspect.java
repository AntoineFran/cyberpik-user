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
public class ControllerAspect {

	@Before("within(com.cda.cyberpik.controller.*)")
	public void logBeforeCallingController(JoinPoint joinpoint) {
		String className = joinpoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinpoint.getSignature().getName();
		log.info("Call to " + className + " with the method " + methodName);
	}
	
	@AfterThrowing(pointcut="within(com.cda.cyberpik.controller.*)", throwing = "ex")
	public void ifControllerThrowsException(JoinPoint joinpoint, Exception ex) {
		String className = joinpoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinpoint.getSignature().getName();
		log.error("Exception on " + className + " with the method " + methodName + ", exception : " + ex.getMessage());
	}
}
