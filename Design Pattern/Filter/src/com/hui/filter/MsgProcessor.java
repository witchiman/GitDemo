package com.hui.filter;

public class MsgProcessor {
	private String msg;
	FilterChain fc;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String process() {		
		return fc.doFilter(msg);
	}

	public FilterChain getFc() {
		return fc;
	}

	public void setFc(FilterChain fc) {
		this.fc = fc;
	}
}
