package io.pivotal.web.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.pivotal.web.domain.Account;
import io.pivotal.web.domain.Order;
import io.pivotal.web.domain.Search;
import io.pivotal.web.service.AccountService;
import io.pivotal.web.service.PortfolioService;
import io.pivotal.web.service.QuotesService;
import io.pivotal.web.service.MarketSummaryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountsController {
	private static final Logger logger = LoggerFactory
			.getLogger(AccountsController.class);
	
	@Autowired
	private QuotesService marketService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MarketSummaryService summaryService;
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String accounts(Model model) {
		logger.debug("/accounts");
		model.addAttribute("marketSummary", summaryService.getMarketSummary());
		
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("accounts: User logged in: " + currentUserName);
		    
		    try {
		    	model.addAttribute("accounts",accountService.getAccounts(currentUserName));
		    } catch (HttpServerErrorException e) {
		    	logger.debug("error retrieving accounts: " + e.getMessage());
		    	model.addAttribute("accountsRetrievalError",e.getMessage());
		    }
		}
		
		return "accounts";
	}
	
	@RequestMapping(value = "/openaccount", method = RequestMethod.GET)
	public String openAccount(Model model) {
		Account account = new Account();
		account.setOpenbalance(new BigDecimal(100000));
		model.addAttribute("newaccount",account);
		return "openaccount";
	}
	
	@RequestMapping(value = "/openaccount", method = RequestMethod.POST)
	public String saveAccount(Model model,@ModelAttribute(value="newaccount") Account account) {
		logger.debug("saveAccounts: creating account: " + account);
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName(); 
		account.setUserid(currentUserName);
		account.setBalance(account.getOpenbalance());
		account.setCreationdate(new Date());
		
		logger.info("saveAccounts: saving account: " + account);
		
		accountService.createAccount(account);
		
		model.addAttribute("marketSummary", summaryService.getMarketSummary());
		model.addAttribute("accounts",accountService.getAccounts(currentUserName));
		
		return "accounts";
	}

	@ExceptionHandler({ Exception.class })
	public ModelAndView error(HttpServletRequest req, Exception exception) {
		logger.debug("Handling error: " + exception);
		logger.warn("Exception: ", exception);
		ModelAndView model = new ModelAndView();
		model.addObject("errorCode", exception.getMessage());
		model.addObject("errorMessage", exception);
		model.setViewName("error");
		return model;
	}

}
