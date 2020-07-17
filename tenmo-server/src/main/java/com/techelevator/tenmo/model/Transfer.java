package com.techelevator.tenmo.model;

public class Transfer {

	private int transferId;
	private int transferTypeId;
	private int transferStatusId;
	private int fromUserId;
	private int toUserId;
	private int accountFrom;
	private int accountTo;
	private double amount;
	private String userNameTo;
	private String userNameFrom;
	private int accountId;
	private String statusDescription;
	private String typeDescription;
	private String displayName;
	private String fromOrTo;

	public String getFromOrTo() {
		return fromOrTo;
	}

	public void setFromOrTo(String fromOrTo) {
		this.fromOrTo = fromOrTo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUserNameTo() {
		return userNameTo;
	}

	public void setUserNameTo(String userNameTo) {
		this.userNameTo = userNameTo;
	}

	public String getUserNameFrom() {
		return userNameFrom;
	}

	public void setUserNameFrom(String userNameFrom) {
		this.userNameFrom = userNameFrom;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTransferId() {
	    return transferId;
	}

	public void setTransferId(int transferId) {
	    this.transferId = transferId;
	}

	public int getTransferTypeId() {
	    return transferTypeId;
	}

	public void setTransferTypeId(int transferTypeId) {
	    this.transferTypeId = transferTypeId;
	}

	public int getTransferStatusId() {
	    return transferStatusId;
	}

	public void setTransferStatusId(int transferStatusId) {
	    this.transferStatusId = transferStatusId;
	}

	public int getAccountFrom() {
	    return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
	    this.accountFrom = accountFrom;
	}

	public int getAccountTo() {
	    return accountTo;
	}

	public void setAccountTo(int accountTo) {
	    this.accountTo = accountTo;
	}

	
	}
	
	

