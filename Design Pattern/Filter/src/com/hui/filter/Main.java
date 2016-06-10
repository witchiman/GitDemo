package com.hui.filter;

public class Main {

	public static void main(String[] args) {
		String str = "大家好:)，什么是敏感，被就业，噼里啪啦的？";
		MsgProcessor mp = new MsgProcessor();
		mp.setMsg(str);
		
		FilterChain fc = new FilterChain();
		fc.addFilter(new HTMLFilter())
			.addFilter(new SensitiveFilter());
		mp.setFc(fc);	
		
		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new FaceFilter());
		
		fc.addFilter(fc2);
		
		String result = mp.process();
		System.out.println(result);
	}

}
