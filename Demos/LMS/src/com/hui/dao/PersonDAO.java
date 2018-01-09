package com.hui.dao;

import com.hui.model.Person;

public interface PersonDAO {
	boolean checkPerson(String name,String password);
	Person showPerson(String name);
}
