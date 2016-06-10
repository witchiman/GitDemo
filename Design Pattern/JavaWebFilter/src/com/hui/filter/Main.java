package com.hui.filter;

import com.hui.filter.*;
import com.hui.filter.web.Request;
import com.hui.filter.web.Response;

public class Main {

	public static void main(String[] args) {
		String str = "大家好:)，什么是敏感，被就业，噼里啪啦的？";	
		Request request = new Request();
		request.setRequestStr(str);
		Response response = new Response();
		response.setResponseStr("response");
		
		FilterChain fc = new FilterChain();
		fc.addFilter(new HTMLFilter())
			.addFilter(new SensitiveFilter());		
				
		fc.doFilter(request, response, fc);
		System.out.println(request.requestStr);
		System.out.println(response.responseStr);
	}

}
