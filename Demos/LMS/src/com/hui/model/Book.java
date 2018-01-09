/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.model;

/**
 *
 */
public class Book {
	private String name;
	private String author;
	private String ISBN;	
	private String category;
	private double price;
	private int number;

	public Book() {
	}
	
	

	public Book(String name, String author, String ISBN, String time, String type, double price, int number) {
		super();
		this.name = name;
		this.author = author;
		ISBN = ISBN;
		this.category = type;
		this.price = price;
		this.number = number;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	 

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}



	
}
