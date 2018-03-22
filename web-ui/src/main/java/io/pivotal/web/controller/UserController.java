package io.pivotal.web.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.pivotal.web.domain.User;
import io.pivotal.web.domain.AuthenticationRequest;
import io.pivotal.web.service.MarketSummaryService;
import io.pivotal.web.service.PortfolioService;
import io.pivotal.web.service.UserService;
import io.pivotal.web.service.AccountService;
import io.pivotal.web.service.QuotesService;

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
public class UserController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private QuotesService marketService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private MarketSummaryService summaryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome(Model model) {
		if (!model.containsAttribute("login")) {
			model.addAttribute("login", new AuthenticationRequest());
		}
		model.addAttribute("marketSummary", summaryService.getMarketSummary());
		
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("User logged in: " + currentUserName);
		    
		    try {
		    	model.addAttribute("accounts",accountService.getAccounts(currentUserName));
		    	model.addAttribute("portfolio",portfolioService.getPortfolio(currentUserName));
		    } catch (HttpServerErrorException e) {
		    	model.addAttribute("portfolioRetrievalError",e.getMessage());
		    }
		    User user = userService.getUser(currentUserName);
		    model.addAttribute("user", user);
		    model.addAttribute("accounts",accountService.getAccounts(currentUserName));
		}
		
		return "index";
	}
	
	//TODO: never gets called?
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//@RequestMapping(value = "/login")
	public String login(Model model, @ModelAttribute(value="login") AuthenticationRequest login) {
		logger.info("Logging in, user: " + login.getUsername());
		//need to add account object to session?
		//CustomDetails userDetails = (CustomDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("Principal: " + SecurityContextHolder.getContext().
				   getAuthentication().getPrincipal());
		Map<String,Object> params = userService.login(login);
		model.addAllAttributes(params);
		//logger.info("got user details, token: " + userDetails.getToken());
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(Model model, @ModelAttribute(value="login") AuthenticationRequest login) {
		logger.info("Logging in GET, user: " + login.getUsername());
		return "index";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String postLogout(Model model, @ModelAttribute(value="login") AuthenticationRequest login) {
		logger.info("Logout, user: " + login.getUsername());
		logger.info(model.asMap().toString());
		return "index";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		User user = new User();
		//user.setBalance(new BigDecimal(100000));
		model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String register(Model model, @ModelAttribute(value="user") User user) {
		logger.info("register: user:" + user.getUserid());
		
		//user.setOpenbalance(user.getBalance());
		user.setCreationdate(new Date());
		
		AuthenticationRequest login = new AuthenticationRequest();
		login.setUsername(user.getUserid());
		model.addAttribute("login", login);
		model.addAttribute("marketSummary", summaryService.getMarketSummary());
		userService.createUser(user);
		return "index";
	}
	@ExceptionHandler({ Exception.class })
	public ModelAndView error(HttpServletRequest req, Exception exception) {
		logger.debug("Handling error: " + exception);
		logger.warn("Exception:", exception);
		ModelAndView model = new ModelAndView();
		model.addObject("errorCode", exception.getMessage());
		model.addObject("errorMessage", exception);
		model.setViewName("error");
		return model;
	}
}
