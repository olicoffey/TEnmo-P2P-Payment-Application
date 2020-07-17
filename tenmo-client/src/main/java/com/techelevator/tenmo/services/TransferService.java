package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class TransferService {
	private String API_BASE_URL;
	private String jwt;

	private final RestTemplate restTemplate = new RestTemplate();
	private ConsoleService console;

	public TransferService(String jwt, String API_BASE_URL, ConsoleService console) {
		this.jwt = jwt;
		this.API_BASE_URL = API_BASE_URL;
		this.console = console;
	}

	public List<User> getAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<User> users = null;
		try {
			User[] userArray = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, request, User[].class)
					.getBody();
			users = Arrays.asList(userArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}

		return users;
	}

	public Transfer updateBalance(Transfer transfer) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity<>(transfer, headers);
		try {
			restTemplate.put(API_BASE_URL + "/accounts", request);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}

		return transfer;
	}

	public Transfer createTransfer(Transfer transfer, double amount, int toUserId, int fromUserId) {
		transfer.setAmount(amount);
		transfer.setToUserId(toUserId);
		transfer.setFromUserId(fromUserId);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity<>(transfer, headers);
		try {
			restTemplate.postForObject(API_BASE_URL + "/transfers", request, Transfer.class);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfer;
	}

	public List<Transfer> showTransfers(int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<Transfer> transfers = null;
		try {
			Transfer[] transferArray = restTemplate.exchange(API_BASE_URL + "/transfers/" + userId, HttpMethod.GET, request, Transfer[].class).getBody();
			transfers = Arrays.asList(transferArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfers;
	}

	public List<Transfer> viewTransferDetails(Integer transferID) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<Transfer> transfers = null;
		try {
			String url = API_BASE_URL + "/transfers/" + transferID +"/details";
			
			Transfer[] transferArray = restTemplate.exchange(url, HttpMethod.GET, request, Transfer[].class).getBody();
			transfers = Arrays.asList(transferArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfers;
		
	}
	

}