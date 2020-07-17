package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public int displayAllUsers(List<User> users) {

		out.printf("%10s %10s", "Users ID", "Name");
		out.println();
		for (User u : users) {
			out.printf("%10s %10s\n", u.getId(), u.getUsername());
		}
		out.println("Enter Id of user you are sending $ to (0 to cancel)");
		String userID = in.nextLine();
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		for (User user : users) {
			int id = user.getId();
			userIds.add(id);
		}
		Integer userId = Integer.parseInt(userID);
		if (userIds.contains(userId)) {
			return userId;
		} else if (userId == 0) {
			return 0;
		} else {
			System.out.println("Please enter a valid id");
			return -1;
		}

	}

	public Transfer createTransfer(int fromUserId, int toUserId) {
		Transfer transfer = new Transfer();
		transfer.setFromUserId(fromUserId);
		transfer.setToUserId(toUserId);

		out.println("amount?");
		String amountToSend = in.nextLine();
		transfer.setAmount(Double.parseDouble(amountToSend));

		return transfer;
	}

	public int displayTransfers(List<Transfer> transfers) {

		out.printf("%-15s %-20s %-15s", "Transfer Id", "From/To", "Amount");
		out.println();
		out.println("---------------------------------------------------");

		for (Transfer t : transfers) {
			out.println(t.getTransferId() + "              " + t.getFromOrTo() + t.getDisplayName() + "            "
					+ String.format("$%.2f", t.getAmount()));
		}

		out.println();
		out.println("Please enter transfer ID to view details (0 to cancel): ");
		String transferID = in.nextLine();
		ArrayList<Integer> transferIds = new ArrayList<Integer>();
		for (Transfer transfer : transfers) {
			int id = transfer.getTransferId();
			transferIds.add(id);
		}
		Integer transferId = Integer.parseInt(transferID);
		if (transferIds.contains(transferId)) {
			return transferId;
		} else if (transferId == 0) {
			return 0;
		} else {
			System.out.println("Please enter a valid id");
			return -1;
		}
	}

	public void displayTransferDetails(List<Transfer> transfers) {
		out.println("-------------------------------");
		out.println("Trasfer Details");
		out.println("-------------------------------");

		for (Transfer t : transfers) {
			out.println("ID: " + t.getTransferId());
			out.println("From: " + t.getUserNameFrom());
			out.println("To: " + t.getUserNameTo());
			out.println("Type: " + t.getTypeDescription());
			out.println("Status: " + t.getStatusDescription());
			out.println("Amount: $" + t.getAmount());
		}
	}

	public void dispalyMessage(String message) {
		out.println(message);
		out.flush();
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt + ": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt + ": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while (result == null);
		return result;
	}

	

	public void printError(String errorMessage) {
		System.err.println(errorMessage);
	}
}
