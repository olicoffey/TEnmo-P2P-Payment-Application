package com.techelevator.tenmo.dao;


import java.util.ArrayList;
import java.util.List;



import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;



@Component
public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> getAllUsers() {

		String SQLSelect = "Select username, user_id from users";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQLSelect);

		List<User> users = new ArrayList<>();

		while (rowSet.next()) {
			User user = mapRowToUser(rowSet);
			users.add(user);
		}
		return users;
	}

	@Override
	public void addFunds(double amount, int toUserId) {
		String selectSQL = "Update accounts set balance = balance+? where user_id =?";
		jdbcTemplate.update(selectSQL, amount, toUserId);
		}	
	
	
	@Override
	public void withdrawFunds(double amount, int fromUserId) {
		addFunds(-amount, fromUserId);
		}

	
	//we need to add the transfer that just happened with addfunds and withdraw funds to the transfer table
	//then to display we need to get all transfers with account id = ?
	@Override
	public Transfer addTransfer(Transfer transfer, double amount, int toUserId, int fromUserId) {
	String selectSQL = "Insert into transfers (transfer_id,transfer_type_id, transfer_status_id, account_from, account_to, amount) Values (DEFAULT, ?,?,?,?,?)";
	jdbcTemplate.update(selectSQL, 2, 2, fromUserId, toUserId, amount);
		
		return transfer;
	}
	
	@Override
	public List<Transfer> showTransfers(int accountId) {
		
		String sqlSelect = "SELECT transfer_id, 'From: ' AS from_or_to, username, amount\n" + 
				"FROM transfers\n" + 
				"JOIN accounts ON transfers.account_from = accounts.account_id\n" + 
				"JOIN users ON accounts.user_id = users.user_id\n" + 
				"WHERE account_to = ?\n" + 
				"AND transfer_type_id = 2\n" + 
				"UNION ALL\n" + 
				"SELECT transfer_id, 'To: ' AS from_or_to, username, amount\n" + 
				"FROM transfers\n" + 
				"JOIN accounts ON transfers.account_to = accounts.account_id\n" + 
				"JOIN users ON accounts.user_id = users.user_id\n" + 
				"WHERE  account_from = ?\n" + 
				"and transfer_type_id = 2";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlSelect, accountId, accountId);
		List<Transfer> transfers = new ArrayList<Transfer>();
		while (rows.next()) {
			Transfer transfer2 = mapRowToTransfer(rows);
			transfers.add(transfer2);
		}
		return transfers;
	}
	
	@Override
	public List<Transfer> showTransferDetailsByTransferId(int transferId) {
		String sqlSelect = "Select transfers.transfer_id, transfers.account_from, u.username as from, users.username as to, transfer_types.transfer_type_desc, transfer_statuses.transfer_status_desc, transfers.amount\n" + 
				"from transfers\n" + 
				"JOIN accounts ON transfers.account_to = accounts.account_id\n" + 
				"JOIN users ON accounts.user_id = users.user_id\n" + 
				"Join transfer_types on transfers.transfer_type_id = transfer_types.transfer_type_id\n" + 
				"Join transfer_statuses on transfers.transfer_status_id = transfer_statuses.transfer_status_id\n" + 
				"join users as u on transfers.account_from = u.user_id\n" +
				"where transfers.transfer_id =?";
				SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlSelect, transferId);
		List<Transfer> transfers = new ArrayList<Transfer>();
		while (rowSet.next()) {
			Transfer transfer2 = mapRowToTransfer2(rowSet);
			transfers.add(transfer2);
		}
		return transfers;
	}
	
	private Transfer mapRowToTransfer2(SqlRowSet rowSet) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowSet.getInt("transfer_id"));
		transfer.setAccountFrom(rowSet.getInt("account_from"));
		transfer.setUserNameFrom(rowSet.getString("from"));
		transfer.setUserNameTo(rowSet.getString("to"));
		transfer.setTypeDescription(rowSet.getString("transfer_type_desc"));
		transfer.setStatusDescription(rowSet.getString("transfer_status_desc"));
		transfer.setAmount(rowSet.getDouble("amount"));
		return transfer;
	}

	private Transfer mapRowToTransfer(SqlRowSet row) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(row.getInt("transfer_id"));
		transfer.setDisplayName(row.getString("username"));
		transfer.setFromOrTo(row.getString("from_or_to"));
//		transfer.setUserNameTo(row.getString("to"));
		transfer.setAmount(row.getDouble("amount"));
//		transfer.setAccountId(row.getInt("account_id"));
//		transfer.setAccountFrom(row.getInt("account_from"));
//		transfer.setAccountTo(row.getInt("account_to"));
		return transfer;
	}

	private User mapRowToUser(SqlRowSet rowSet) {
		User user = new User();
		user.setId(rowSet.getLong("user_id"));
		user.setUsername(rowSet.getString("username"));
		return user;
	}

	

	
	

}
