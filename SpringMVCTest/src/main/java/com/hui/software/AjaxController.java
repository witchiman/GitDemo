package com.hui.software;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);
	
	@RequestMapping("/ajaxrequest")
	public String requestAjax() {
		logger.info("收到Ajax的请求！");
		
		return "ajaxtest";
	}
	
	
	/*该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，
	写入到Response对象的body数据区。*/
	@RequestMapping(value = "ajaxhandle", method=RequestMethod.POST)
	public @ResponseBody List<User> handleAjax(String name) throws Exception {
		
		String userName=new String(name.getBytes("iso-8859-1"),"UTF-8");  //解决乱码
		logger.info("The user name is " + userName);
		
		List<User> list = new ArrayList<User>();
		list.add(new User(userName));
		list.add(new User("来自异世界的你"));
		
		return list;
	}

}
