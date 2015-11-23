package com.web.controller;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.FoodDAO;
import com.model.Food;
import com.model.MenuWeek;
import com.model.User;


@Controller
public class FoodController {
	
    @Resource(name = "foodDAOimpl")
    private FoodDAO food;

	@RequestMapping(value = "food", method = RequestMethod.GET)
	public String food(Model model){
		
		MenuWeek mw = new MenuWeek();
		mw.setWeek(1);
		
    	Food tt = food.findByName("Украинский борщ");
    	Set<Food> f = new HashSet<>();
    	f.add(tt);
    	
    	mw.setFood(f);
		
		model.addAttribute("Menu", mw);
		return "food";
	}
	
	@RequestMapping(value="food", method = RequestMethod.POST)
	public String foodPost(MenuWeek menur, BindingResult result, ModelMap model){

		return "food";
	}
}
