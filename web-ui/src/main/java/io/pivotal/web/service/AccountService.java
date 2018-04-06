package io.pivotal.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.web.domain.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class AccountService {
	private static final Logger logger = LoggerFactory
			.getLogger(AccountService.class);
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Value("${pivotal.accountsService.name}")
	private String accountsService;
	
	public void createAccount(Account account) {
		logger.debug("Creating account for userId: " + account.getUserid());
		String status = restTemplate.postForObject("http://" + accountsService + "/accounts/", account, String.class);
		logger.info("Status from registering account for "+ account.getUserid()+ " is " + status);
	}

	
	// The below is commented out to demonstrate impact of lack of hystrix, and can be uncommented during presentation
//	 @HystrixCommand(fallbackMethod = "getAccountsFallback")
	public List<Account> getAccounts(String user) {
		logger.debug("Looking for account with userId: " + user);
		
	    Account[] accounts = restTemplate.getForObject("http://" + accountsService + "/accounts?name={user}", Account[].class, user);
	    
	    return Arrays.asList(accounts);
	}

	public List<Account> getAccountsFallback(String user) {
		logger.warn("Invoking fallback for getAccount");
		return new ArrayList<>();
	}

	public List<Account> getAccountsByType(String user, String type) {
		logger.debug("Looking for account with userId: " + user + " and type: " + type);
		
	    Account[] accounts = restTemplate.getForObject("http://" + accountsService + "/accounts?name={user},type={type}", Account[].class, user,type);

	    return Arrays.asList(accounts);
	}

}
