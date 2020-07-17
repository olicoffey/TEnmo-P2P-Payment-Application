package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	private TransferDAO transferDAO;

	public TransferController(TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
	}

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return transferDAO.getAllUsers();
	}

	@RequestMapping(path = "/accounts", method = RequestMethod.PUT)
	public void update(@RequestBody Transfer transfer) {
		transferDAO.addFunds(transfer.getAmount(), transfer.getToUserId());
		transferDAO.withdrawFunds(transfer.getAmount(), transfer.getFromUserId());

	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path="/transfers", method = RequestMethod.POST)
	public Transfer createTransfer(@RequestBody Transfer transfer) {
	return transferDAO.addTransfer(transfer, transfer.getAmount(), transfer.getToUserId(), transfer.getFromUserId());
	}
	
	@RequestMapping(path="/transfers/{id}", method=RequestMethod.GET)
	public List<Transfer> showTransfers(Principal principal, @PathVariable(name="id") int accountId ) {
		return transferDAO.showTransfers(accountId);
	}
	
	@RequestMapping(path="/transfers/{id}/details", method=RequestMethod.GET)
	public List<Transfer> showTransferDetailsByTransferId(Principal principal, @PathVariable(name="id") int transferId ) {
		return transferDAO.showTransferDetailsByTransferId(transferId);
	}
	
}
