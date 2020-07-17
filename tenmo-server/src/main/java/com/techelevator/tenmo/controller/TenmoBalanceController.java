package com.techelevator.tenmo.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.AccountBalance;

@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoBalanceController {

	private AccountDAO accountDao; 
	
	public TenmoBalanceController(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	
	@RequestMapping(path = "/accounts", method=RequestMethod.GET)
	public AccountBalance get(Principal principal)  {
		return accountDao.getBalance(principal.getName());
	}
}
