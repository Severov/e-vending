package com.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.FoodDAO;
import com.editor.FoodEditor;
import com.model.Food;
import com.model.MenuWeek;
import com.model.Realization;



@Controller
public class FoodController {
	
    @Resource(name = "foodDAOimpl")
    private FoodDAO food;
    
    @Resource(name = "foodEditor")
    private FoodEditor foodEditor;

	@RequestMapping(value = "/food", method = RequestMethod.GET)
	public String food(Model model){
		
		MenuWeek mw = new MenuWeek();
		mw.setWeek(1);    

		model.addAttribute("menu", mw);
		return "food";
	}
	
	@ModelAttribute("listFood")
	public List<Food> getlist()
	{
		return food.getAllFood();
	}
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Food.class, foodEditor);
    }
	
	@RequestMapping(value = "/food", method = RequestMethod.POST)
	public String foodPost(@ModelAttribute("menu") MenuWeek menu, BindingResult result, ModelMap model){
		
		for( Food entry : menu.getFood() ){
    		System.out.print( entry.getName());
    	}

		return "food";
	}
}
