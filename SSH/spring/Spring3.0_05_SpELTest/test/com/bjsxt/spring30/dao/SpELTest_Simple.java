package com.bjsxt.spring30.dao;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bjsxt.spring30.model.User;

public class SpELTest_Simple {
	@Test
	public void test01() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello,World'");
		System.out.println((String)exp.getValue());
	}
	
	@Test
	public void test02() {
		
		User u = new User();
		u.setUsername("zhangsan");
		
		EvaluationContext context = new StandardEvaluationContext(u);
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("username"); //u.getUsername()
		System.out.println((String)exp.getValue(context));
		System.out.println((String)exp.getValue(u));
	}
	
	//"new HelloWorld().sayHello()"
	//eval("alert('ok');");
}
