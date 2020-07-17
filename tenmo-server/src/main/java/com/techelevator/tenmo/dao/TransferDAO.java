package com.techelevator.tenmo.dao;



import java.util.List;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {

	List<User> getAllUsers();
	void addFunds(double amount, int toUserId);
	void withdrawFunds(double amount, int fromUserId);
	public Transfer addTransfer(Transfer transfer, double amount, int toUserId, int fromUserId);
	public List <Transfer> showTransfers(int accountId);
	public List<Transfer> showTransferDetailsByTransferId(int transferId);
}
