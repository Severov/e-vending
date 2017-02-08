package com.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.UserDAO;
import com.model.User;

/**
 * Контроллер аутентификации пользователя
 * 
 * @author mishka
 *
 */
@Controller
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource(name = "userService")
	private UserDAO userService;

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/private/main";
		} else{
			if (error != null) {
				model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
			}

			if (logout != null) {
				model.addAttribute("msg", "Выход выполнен");
			}
			
			return "login";
		}
	}
	
	@RequestMapping(value = "ws/authorize",	method = {RequestMethod.POST})
	@ResponseBody
	public String authorize(@RequestHeader(value = "apiKey") String apiKey, HttpServletResponse response) {		
		logger.info(apiKey);
		User user = userService.getUserByApiKey(apiKey);
		if(user == null){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null; 
		}

        UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);  
        return "ok";
	}
	
	@RequestMapping(value="/csrf-token", method=RequestMethod.GET)
	@ResponseBody
	public String getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Не верный логин/пароль";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Не верный логин/пароль";
		}

		return error;
	}
}
