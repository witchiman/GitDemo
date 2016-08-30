package com.hui.software;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/test", method = RequestMethod.GET)		//与方法名上的RequestMapping，组合成路径
@SessionAttributes({"arg1","arg2"}) 				//此注解会将ModelMap中属性名为"arg1“、”arg2“的参数再次放入session中
public class SecondController {
		
	private static final Logger logger =  LoggerFactory.getLogger(SecondController.class);
	
	
	/*无参数请求,方法名可自定义*/
	@RequestMapping
	public String apply(Locale locale, Model model) { 
		logger.info("this is the method of apply");
		return "test";
	}
	
	
	/**
	 * 带参数的请求
	 * @param method 参数名需与实参一样  
	 * @return 
	 */
	@RequestMapping(params="method=method1")
	public String apply1(String method) {
		logger.info("this is the method：" + method);
		return "method1";
	}
	
	@RequestMapping(params="method=method2")
	public String apply2(String method) {
		logger.info("This is the method of :" + method);
		return "method2";
	}
	

	/**
	 * 接收多个参数
	 * 形参前的RequestParam用以传递请求参数,必须指定对应参数名
	 * @param arg1 “name”
	 * @param arg2 "age"
	 * @return
	 */
	@RequestMapping("/mutiparams")
	public String apply3(@RequestParam("name") String arg1,@RequestParam("age") String arg2) {
		logger.info("this is method 3:" + arg1 +"|" + arg2);
		return "method3";
	}
	
	@RequestMapping("/request")	
	public String apply4(Model model,HttpServletRequest req, ModelMap map) {
		model.addAttribute("model","This is a value from model!");
		req.setAttribute("request", "This is a value from request!");
		req.getSession().setAttribute("session", "This is a value from seesion!");
		map.addAttribute("modelmap", "This is a value from ModelMap!");
		map.addAttribute("arg1","This is a value to test SessionAttributes!");
		return "test";
	}
	
	
	@RequestMapping(params="method=method3")
	public String apply5(@ModelAttribute("arg1") String value, ModelMap modelMap) {  //ModelAttribute配合SessionAttributes使用，可将ModelMap
		logger.info("apply5 : " + value);											//中的值通过该注解赋值给指定变量
		return "test";
	}
	
	/*重定向，前进*/
	@RequestMapping(params="method=method4")
	public String apply6(ModelMap mm) {
		return "redirect:/";			//使用相对路径
		//return "redirect:https://www.baidu.com";
		//return "forward:/";          //使用相对路径,由于默认为forward，可以不写forward
	}
	
	/*通过返回ModelAndView传递对象*/
	@RequestMapping(params="method=method5")
	public ModelAndView apply7(String method) {
		logger.info("This is apply7");
		ModelAndView mv = new ModelAndView("test");
		User u = new User("辉哥哥");
		mv.addObject("user", u);
		
		return mv;
		
	}
	
}
