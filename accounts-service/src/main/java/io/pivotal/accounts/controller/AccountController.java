package io.pivotal.accounts.controller;

import java.math.BigDecimal;
import java.util.List;

import io.pivotal.accounts.domain.Account;
import io.pivotal.accounts.domain.AccountType;
import io.pivotal.accounts.domain.Transaction;
import io.pivotal.accounts.domain.TransactionType;
import io.pivotal.accounts.service.AccountService;

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
 * <li>GET <code>/accounts/{id}</code> retrieves the account with given id.
 * <li>POST <code>/accounts</code> stores the account object passed in body.
 * <li>POST <code>/accounts/transaction</code> receives a transaction to
 * process.
 * </ul>
 * <p>
 * 
 * @author David Ferreira Pinto
 * @author Maxim Avezbakiev
 *
 */
@RestController
public class AccountController {

	private static final Logger logger = LoggerFactory
			.getLogger(AccountController.class);

	/**
	 * The service to delegate calls to.
	 */
	@Autowired
	private AccountService service;

	/**
	 * REST call to retrieve the account with the given id.
	 * 
	 * @param id
	 *            The id of the account to retrieve the account for.
	 * @return The account object if found.
	 */
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Account> find(@PathVariable("id") final Integer id) {

		logger.info("AccountController.find: id=" + id);

		Account accountResponse = this.service.findAccount(id);
		return new ResponseEntity<Account>(accountResponse,
				getNoCacheHeaders(), HttpStatus.OK);

	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> findAccounts(
			@RequestParam(value = "name") final String id,
			@RequestParam(value = "type", required = false) final String type) {

		logger.info("AccountController.findAccount: id=" + id);
		if (type == null) {
			List<Account> accountResponse = this.service.findAccounts(id);
			return new ResponseEntity<List<Account>>(accountResponse,
					getNoCacheHeaders(), HttpStatus.OK);
		} else {
			List<Account> accountResponse = this.service.findAccountsByType(id,
					AccountType.valueOf(type));
			return new ResponseEntity<List<Account>>(accountResponse,
					getNoCacheHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * REST call to save the account provided in the request body.
	 * 
	 * @param accountRequest
	 *            The account to save.
	 * @param builder
	 * @return
	 */
	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody Account accountRequest,
			UriComponentsBuilder builder) {

		logger.debug("AccountController.save: userId="
				+ accountRequest.getUserid());

		Integer accountProfileId = this.service.saveAccount(accountRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(builder.path("/account/{id}")
				.buildAndExpand(accountProfileId).toUri());
		return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
	}

	//TODO move logic to AccountService!
	/**
	 * REST call to process a transaction of an account.
	 * 
	 * @param transaction the transaction to process.
	 * @return a response entity with either SUCCESS or FAILED.
	 */
	@RequestMapping(value = "/accounts/transaction", method = RequestMethod.POST)
	public ResponseEntity<String> transaction(
			@RequestBody Transaction transaction) {
		logger.debug("AccountController.transaction: " + transaction.toString());
		if (transaction.getType().equals(TransactionType.DEBIT)) {
			logger.debug("debit transaction");
			Account accountResponse = this.service.findAccount(transaction
					.getAccountId());

			BigDecimal currentBalance = accountResponse.getBalance();

			BigDecimal newBalance = currentBalance.subtract(transaction
					.getAmount());

			if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
				accountResponse.setBalance(newBalance);
				this.service.saveAccount(accountResponse);
				// TODO save transaction?
				logger.debug("transaction processed.");
				return new ResponseEntity<String>("SUCCESS",
						getNoCacheHeaders(), HttpStatus.OK);

			} else {
				// no sufficient founds available
				return new ResponseEntity<String>("FAILED",
						getNoCacheHeaders(), HttpStatus.EXPECTATION_FAILED);
			}

		} else if (transaction.getType().equals(TransactionType.CREDIT)) {
			logger.debug("credit transaction");
			Account accountResponse = this.service.findAccount(transaction.getAccountId());

			BigDecimal currentBalance = accountResponse.getBalance();

			logger.debug("AccountController.transaction: current balance='"
					+ currentBalance + "'.");

			if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {

				BigDecimal newBalance = currentBalance.add(transaction.getAmount());
				logger.debug("AccountController.increaseBalance: new balance='"
						+ newBalance + "'.");

				accountResponse.setBalance(newBalance);
				this.service.saveAccount(accountResponse);
				// TODO save transaction?
				return new ResponseEntity<String>("SUCCESS", getNoCacheHeaders(), HttpStatus.OK);

			} else {
				// amount can not be negative for increaseBalance, please use
				// decreaseBalance
				return new ResponseEntity<String>("FAILED", getNoCacheHeaders(),
						HttpStatus.EXPECTATION_FAILED);
			}

		}
		return null;
	}

	private HttpHeaders getNoCacheHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Cache-Control", "no-cache");
		return responseHeaders;
	}
}
