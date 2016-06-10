package com.hui.filter;

import java.util.ArrayList;
import java.util.List;
import com.hui.filter.web.Request;
import com.hui.filter.web.Response;

public class FilterChain implements Filter {
	List<Filter> filters = new ArrayList<Filter>();
	int index = 0;
	
	public FilterChain addFilter(Filter f) {
		filters.add(f);
		return this;
	}

	@Override
	public void doFilter(Request request, Response response, FilterChain chain) {
		if(index == filters.size()) return;		
		Filter f = filters.get(index);
		index++;
		f.doFilter(request, response, chain);
	}
		
	
	 

}
