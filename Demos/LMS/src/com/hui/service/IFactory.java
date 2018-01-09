package com.hui.service;

import com.hui.dao.PersonDAO;

public interface IFactory {
	PersonDAO createPersonDAO();
}
