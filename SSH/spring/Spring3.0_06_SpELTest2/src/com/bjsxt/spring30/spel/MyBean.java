package com.bjsxt.spring30.spel;

public class MyBean {
	private double randomNumber;
	private String userRegion;
	public double getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(double randomNumber) {
		this.randomNumber = randomNumber;
	}
	public String getUserRegion() {
		return userRegion;
	}
	public void setUserRegion(String userRegion) {
		this.userRegion = userRegion;
		//System.getProperty("user.country");
	}
	
}
