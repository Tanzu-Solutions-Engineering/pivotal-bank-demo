package io.pivotal.user.service;

import java.util.*;

import io.pivotal.user.domain.User;
import io.pivotal.user.exception.AuthenticationException;
import io.pivotal.user.exception.NoRecordsFoundException;
import io.pivotal.user.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * The service in the user microservice.
 * 
 * @author David Ferreira Pinto
 *
 */
@Service
public class UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	/**
	 * The User repository.
	 */
	@Autowired
	UserRepository user;

	/**
	 * Retrieve a user with given id. The id here is the unique id value of the
	 * account managed by the repository (auto-increment).
	 * 
	 * @param id
	 *            The id of the account.
	 * @return The account object if found or throws a NoRecordsFoundException.
	 */
	public User findUser(Integer id) {

		logger.debug("user.findAccount: id=" + id);

		Optional<User> account = user.findById(id);
		if (!account.isPresent()) {
			logger.warn("UserService.findAccount: could not find account with id: "
					+ id);
			throw new NoRecordsFoundException();
		}

		logger.info(String
				.format("UserService.findAccount - retrieved account with id: %s. Payload is: %s",
						id, account.get()));

		return account.get();
	}

	/**
	 * Retrieve a user with given username. The id here is the unique user id
	 * value of the account, ie the username.
	 * 
	 * @param username
	 *            The user name of the account.
	 * @return The user object if found or throws a NoRecordsFoundException.
	 */
	public User findUser(String username) {

		logger.debug("UserService.findAccount: username=" + username);

		User account = user.findByUserid(username);
		if (account == null) {
			logger.warn("UserService.findAccount: could not find user with id: "
					+ username);
			throw new NoRecordsFoundException();
		}

		logger.info(String
				.format("UserService.findAccount - retrieved user with id: %s. Payload is: %s",
						username, account));

		return account;
	}

	/**
	 * Retrieves the account by the authorization token associated with that
	 * account and currejavant login.
	 * 
	 * @param token
	 *            The token to search for.
	 * @return The account object if found or AuthenticationException otherwise.
	 */
	@Cacheable(value = "authorizationCache")
	public User findAccountprofileByAuthtoken(String token) {
		logger.debug("UserService.findAccountprofileByAuthtoken looking for authToken: "
				+ token);
		if (token == null) {
			// TODO: no point in checking database. throw exception here.
			logger.error("UserService.findAccountprofileByAuthtoken(): token is null");
			throw new AuthenticationException("Authorization Token is null");
		}
		User accountProfile = null;
		accountProfile = user.findByAuthtoken(token);
		if (accountProfile == null) {
			logger.error("UserService.findAccountprofileByAuthtoken(): accountProfile is null for token="
					+ token);
			throw new AuthenticationException("Authorization Token not found");
		}

		return accountProfile;
	}

	/**
	 * Saves the given account in the repository.
	 * 
	 * @param accountRequest
	 *            The account to save.
	 * @return the id of the account.
	 */
	public Integer saveUser(User userRequest) {

		logger.debug("UserService.saveAccount:" + userRequest.toString());
		// need to set some stuff that cannot be null!
		if (userRequest.getLogincount() == null) {
			userRequest.setLogincount(0);
		}
		if (userRequest.getLogoutcount() == null) {
			userRequest.setLogoutcount(0);
		}

		User account = user.save(userRequest);
		logger.info("UserService.saveAccount: account saved: " + account);
		return account.getId();
	}

	/**
	 * Attempts to login the user with the given username and password. Throws
	 * AuthenticationException if an account with the given username and
	 * password cannot be found.
	 * 
	 * @param username
	 *            The username to login.
	 * @param password
	 *            The password to use.
	 * @return a map with the authtoken, account Id.
	 */
	public Map<String, Object> login(String username, String password) {
		logger.debug("login in user: " + username);
		Map<String, Object> loginResponse = null;
		User account = user.findByUseridAndPasswd(username, password);
		if (account != null) {
			logger.debug("Found Account for user: " + username);
			account.setAuthtoken(UUID.randomUUID().toString());
			account.setLogincount(account.getLogincount() + 1);
			account.setLastlogin(new Date());
			account = user.save(account); // persist new auth token and last
											// login
			loginResponse = new HashMap<String, Object>();

			loginResponse.put("authToken", account.getAuthtoken());
			loginResponse.put("accountid", account.getId());
			// loginResponse.put("password", account.getPasswd());

			logger.info("UserService.login success for " + username
					+ " username::token=" + loginResponse.get("authToken"));

		} else {
			logger.warn("UserService.login failed to find username=" + username
					+ " password=" + password);
			throw new AuthenticationException("Login failed for user: "
					+ username);
		}
		return loginResponse;
	}

	/**
	 * logs the give user out of the system.
	 * 
	 * @param userId
	 *            the userid to logout.
	 * @return The account object or null;
	 */
	public User logout(String userId) {
		logger.debug("UserService.logout: Logging out account with userId: "
				+ userId);
		User account = user.findByUserid(userId);
		if (account != null) {
			account.setAuthtoken(null); // remove token
			account.setLogoutcount(account.getLogoutcount() + 1);
			user.save(account);
			logger.info("UserService.logout: Account logged out: "
					+ account.getUserid());
		} else {
			logger.warn("UserService.logout: Could not find account to logout with userId: "
					+ userId);
		}
		return account;
	}
}
