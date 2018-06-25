package io.pivotal.web.controller;

import io.pivotal.web.domain.*;
import io.pivotal.web.service.AnalyticsService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AnalyticsController {

	private static final Logger logger = LoggerFactory
			.getLogger(AnalyticsController.class);
	
	@Autowired
	private AnalyticsService analyticsService;
	
	@RequestMapping(value = "/analytics", method = RequestMethod.GET)
	public String showTrade(Model model) {
		logger.debug("/analytics.GET");
		//model.addAttribute("marketSummary", marketService.getMarketSummary());
		
		model.addAttribute("search", new Search());
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("User logged in: " + currentUserName);
		}
		
		return "analytics";
	}
	@RequestMapping(value = "/analytics", method = RequestMethod.POST)
	public String showTrade(Model model, @ModelAttribute("search") Search search) {
		logger.debug("/analytics.POST - symbol: " + search.getName());
		
		model.addAttribute("search", search);
		
		if (search.getName() == null || search.getName().equals("") ) {
			model.addAttribute("trades", new ArrayList<Trade>());
		} else {
			model.addAttribute("trades", analyticsService.getTrades(search.getName()));
		}
		//check if user is logged in!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    logger.debug("User logged in: " + currentUserName);
		}
		
		return "analytics";
	}
}
