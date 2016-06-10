package com.hui.filter;

import com.hui.filter.web.Request;
import com.hui.filter.web.Response;

public class HTMLFilter implements Filter {

	@Override
	public void doFilter(Request request, Response response, FilterChain chain) {
		request.requestStr = request.requestStr.replace("<","(")
									.replace(">",")") + "-->HTMLFilter";
		chain.doFilter(request, response, chain);		
		response.responseStr += "-->HTMLFilter";
	}	


}
