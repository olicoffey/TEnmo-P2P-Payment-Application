package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {
	private String API_BASE_URL;
	private String jwt;

	private final RestTemplate restTemplate = new RestTemplate();
	private ConsoleService console;
	
	public UserService(String jwt, String API_BASE_URL, ConsoleService console) {
		this.jwt = jwt;
		this.API_BASE_URL = API_BASE_URL;
		this.console = console;
	}
	
	
	public User findUserByAccountId(int accountId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		User user = new User();
		try {
		user = restTemplate.exchange(API_BASE_URL + "accounts/"+ accountId + "/users", HttpMethod.GET, request, User.class).getBody();
		} catch (RestClientResponseException ex) {
		console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
		console.printError(ex.getMessage());
		}
		return user;
		}
	
		
		
}
