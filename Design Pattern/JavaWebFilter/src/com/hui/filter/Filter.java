package com.hui.filter;

import com.hui.filter.web.Request;
import com.hui.filter.web.Response;

public interface Filter {
	public void  doFilter(Request request, Response response, FilterChain chain);
}
