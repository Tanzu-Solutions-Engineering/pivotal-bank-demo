package io.pivotal.web.service;

import java.util.Map;

import io.pivotal.web.domain.AuthenticationRequest;
import io.pivotal.web.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Value("${pivotal.userService.name}")
	private String userService;
	
	public void createUser(User user) {
		logger.debug("Creating user with userId: " + user.getUserid());
		logger.debug(user.toString());
		String status = restTemplate.postForObject("http://" + userService + "/users/", user, String.class);
		logger.info("Status from registering account for "+ user.getUserid()+ " is " + status);
	}
	
	public Map<String,Object> login(AuthenticationRequest request){
		logger.debug("logging in with userId:" + request.getUsername());
		@SuppressWarnings("unchecked")
		Map<String,Object> result = (Map<String, Object>) restTemplate.postForObject("http://" + userService + "/login/".toString(), request, Map.class);
		return result;
	}
	
	public User getUser(String user) {
		logger.debug("Looking for user with user name: " + user);
		
	    User account = restTemplate.getForObject("http://" + userService + "/users/{user}", User.class, user);
	    logger.debug("Got user: " + account);
	    return account;
	}
	
	public void logout(String user) {
		logger.debug("logging out user with userId: " + user);
		
	    ResponseEntity<?> response = restTemplate.getForEntity("http://" + userService + "/logout/{user}", String.class, user);
	    logger.debug("Logout response: " + response.getStatusCode());
	}
	
}
