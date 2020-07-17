package com.techelevator.tenmo.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.AccountBalance;
@Component
public class JDBCAccountDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCAccountDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public AccountBalance getBalance(String username) {
		AccountBalance accountBalance = new AccountBalance();
		String SQLSelect = "Select accounts.user_id, accounts.balance, accounts.account_id from accounts join users on accounts.user_id = users.user_id where users.username = ?"; 
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQLSelect, username);
		
		if(rowSet.next()) {
			accountBalance = mapRowToAccountBalance(rowSet);
		}
		return accountBalance;
	}
	
	private AccountBalance mapRowToAccountBalance(SqlRowSet row) {
		AccountBalance accountBalance = new AccountBalance();
		
		accountBalance.setAccountId(row.getInt("account_id"));
		accountBalance.setUserId(row.getInt("user_id"));
		accountBalance.setBalance(row.getDouble("balance"));
		return accountBalance;
	}

}
