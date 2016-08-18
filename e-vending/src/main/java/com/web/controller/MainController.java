package com.web.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "redirect:/private_room";
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

	@RequestMapping(value = "/private/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("private/admin");

		return model;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}

}