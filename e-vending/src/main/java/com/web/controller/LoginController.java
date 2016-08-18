package com.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер аутентификации пользователя
 * 
 * @author mishka
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/private/admin";
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
