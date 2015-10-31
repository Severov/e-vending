package com.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
import com.model.User;

@Controller
public class RegistrationController {
	
	@Resource(name="userDaoImpl")
	private UserDao userDao;
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(Model model){
	    model.addAttribute("user", new User("", "", false));
		return "registration";
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public String registrationsdaf(@Validated User user, BindingResult result, String password_confirm, ModelMap model){
 
        if(result.hasErrors()) {
                return "enroll";
        }
        
        if (user.getUsername().isEmpty() || user.getFullname().isEmpty()){
        	model.addAttribute("error", "Заполните все поля!");
        	return "registration";
        }
        
        if (!user.getPassword().equals(password_confirm)){
        	model.addAttribute("error", "Пароли не совпадают!");
        	return "registration";
        }
        
        userDao.saveUser(user);
		return "redirect:/admin";
	}
}
