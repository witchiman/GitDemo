package com.hui.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
	List<Filter> filters = new ArrayList<Filter>();
	
	public FilterChain addFilter(Filter f) {
		filters.add(f);
		return this;
	}
	
	@Override
	public String doFilter(String msg) {
		String r = msg;
		for(Filter f : filters) {
			r =  f.doFilter(r);
		}
		return r;
	}
	 

}
