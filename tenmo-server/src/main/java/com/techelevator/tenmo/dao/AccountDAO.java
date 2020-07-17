package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountBalance;

public interface AccountDAO {

AccountBalance getBalance(String username);
	
}
