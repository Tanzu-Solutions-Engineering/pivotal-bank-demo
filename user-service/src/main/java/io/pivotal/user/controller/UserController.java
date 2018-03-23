package io.pivotal.user.controller;

import java.math.BigDecimal;

import io.pivotal.user.domain.User;
import io.pivotal.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for the accounts microservice. Provides the following
 * endpoints:
 * <p>
 * <ul>
 * <li>GET <code>/users/{id}</code> retrieves the user with given id.
 * <li>POST <code>/users</code> stores the account object passed in body.
 * </ul>
 * <p>
 * 
 * @author David Ferreira Pinto
 * @author Maxim Avezbakiev
 *
 */
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * The service to delegate calls to.
	 */
	@Autowired
	private UserService service;

	/**
	 * REST call to retrieve the account with the given id as userId.
	 * 
	 * @param id
	 *            The id of the user to retrieve.
	 * @return The user object if found.
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> find(@PathVariable("username") final String username) {

		logger.info("UserController.find: username=" + username);

		User userResponse = this.service.findUser(username);
		return new ResponseEntity<User>(userResponse, getNoCacheHeaders(), HttpStatus.OK);

	}

	/**
	 * REST call to save the user provided in the request body.
	 * 
	 * @param userRequest
	 *            The user to save.
	 * @param builder
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody User userRequest, UriComponentsBuilder builder) {

		logger.debug("UserController.save: userId=" + userRequest.getUserid());

		Integer accountProfileId = this.service.saveUser(userRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(builder.path("/users/{id}").buildAndExpand(accountProfileId).toUri());
		return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
	}

	private HttpHeaders getNoCacheHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Cache-Control", "no-cache");
		return responseHeaders;
	}
}
