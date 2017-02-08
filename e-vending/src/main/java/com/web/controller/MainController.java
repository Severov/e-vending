package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dao.UserDAO;

@Controller
public class MainController {
	
	@Autowired
	UserDAO userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "redirect:/private/main";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/private/hello", method = RequestMethod.GET)
	public ModelAndView pageHello() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "This is title hello");
		model.setViewName("private/hello");
		return model;
	}

	@RequestMapping(value = "/private/main", method = RequestMethod.GET)
	public ModelAndView privateMain() {
		ModelAndView model = new ModelAndView();
		model.addObject("root_user", userService.getAuthenticationUser().getRoot_user());
		model.setViewName("private/main");
		return model;
	}
	
	@RequestMapping(value = "/private/map", method = RequestMethod.GET)
	public String privateMap() {
		return "private/googleMap";
	}
	
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public String charts() {
		return "charts";
	}

	@RequestMapping(value = "/private/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("private/admin");

		return model;
	}

}