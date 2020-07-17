package com.techelevator.tenmo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;


@RestController
public class UserController {

	private UserDAO userDao;
	
	public UserController(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	@RequestMapping(path = "/accounts/{id}/users", method = RequestMethod.GET)
	public User findUserByAccountId(@PathVariable("id") int accountId) {
	return userDao.findUserByAccountId(accountId);
	}
	
}
