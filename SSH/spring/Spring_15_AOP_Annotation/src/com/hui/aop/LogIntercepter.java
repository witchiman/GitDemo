package com.hui.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogIntercepter {
	//«–»Îµ„”Ô∑®
	//@Before("execution(public void com.hui.dao.impl.UserDAOImpl.save(com.hui.model.User))")
	//@Pointcut("execution(public * com.hui.dao..*.*(..))")
	@Pointcut("execution(public * com.hui.service..*.add(..))")
	public void myMethod() {};
	
	@Before("myMethod()")
	public void before() {
		System.out.println("method before start:");
	}
	
	@AfterReturning("execution(public * com.hui.dao..*.*(..))")
	public void afterReturning() {
		System.out.println("method afterreturning°£");
	}
	
	@AfterThrowing("myMethod())")
	public void afterThrowing() {
		System.out.println("method afterthorowing!:");
	}
	
	@Around("myMethod()")
	public void myMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("method around start:");
		pjp.proceed();
		System.out.println("method around end!");
	}
	
	
	
}
