package com.web.controller;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
import com.model.User;

@Controller
public class RegistrationController {

	@Resource(name = "userService")
	private UserDao userService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationsdaf(@ModelAttribute("user") @Validated User user, BindingResult result, String password_confirm, ModelMap model) {

		if (result.hasErrors()) {
			return "enroll";
		}

		if (user.getUsername().isEmpty() || user.getFullname().isEmpty()) {
			model.addAttribute("error", "Заполните все поля!");
			return "registration";
		}

		if (!user.getPassword().equals(password_confirm)) {
			model.addAttribute("error", "Пароли не совпадают!");
			return "registration";
		}

		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userService.save(user);
		return "redirect:/login";
	}
}
