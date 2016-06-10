package com.hui.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
 
@Component
public class LogIntercepter {
	 
	public void myMethod() {}; 
	public void before() {
		System.out.println("method before start:");
	}
	
	public void afterReturning() {
		System.out.println("method afterreturning¡£");
	} 
	
	public void afterThrowing() {
		System.out.println("method afterthorowing!:");
	}
	 
	public void myMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("method around start:");
		pjp.proceed();
		System.out.println("method around end!");
	}
	
	
	
}
