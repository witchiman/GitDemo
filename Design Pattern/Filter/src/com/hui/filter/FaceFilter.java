package com.hui.filter;

public class FaceFilter implements Filter {

	@Override
	public String doFilter(String str) {
		String r = str.replace(":)", "^v^");
		return r;
	}

}
