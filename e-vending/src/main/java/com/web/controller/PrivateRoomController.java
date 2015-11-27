/*
 * Copyright 2015 e-vending.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.web.controller;

import com.dao.FoodDAO;
import com.dao.MenuWeekDAO;
import com.dao.RealizationDAO;
import com.dao.UserDao;
import com.editor.FoodEditor;
import com.model.Food;
import com.model.MenuWeek;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author mishka
 */
@Controller
public class PrivateRoomController {

    @Resource(name = "menuWeekService")
    private MenuWeekDAO menuWeekService;
    
    @Resource(name = "foodDAOimpl")
    private FoodDAO foodService;
    
    @Resource(name = "realizationDAOimpl")
    private RealizationDAO real;
    
	@Resource(name="userDaoImpl")
	private UserDao userDao;
	
    @Resource(name = "foodEditor")
    private FoodEditor foodEditor;
	
	@ModelAttribute("listFood")
	public List<Food> getlist()
	{
		return foodService.getAllFood();
	}
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Food.class, foodEditor);
    }
	
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String add_module(Model model) {  
        model.addAttribute("menu",  menuWeekService.getAllmenu());
        return "/menu";
    }
    
    // Клиент формирует меню
    @RequestMapping(value = "/food-list", method = RequestMethod.GET)
    public String foodListGET(Model model) {    
        model.addAttribute("menu",  menuWeekService.getAllmenu());
        return "/menu";
    }
    
   // @RequestMapping(value = "/food-list", method = RequestMethod.GET)
   // public String foodListGet(Model model) {    	
   //     model.addAttribute("foodList",  menuWeekService.getAllmenu());
   //     return "/food-list";
   // }
      
    @RequestMapping(value = "/week-list", method = RequestMethod.GET)
	public ModelAndView weekListFoodGet(@RequestParam(value = "week", required = false) Integer week) {
		ModelAndView model = new ModelAndView();
		//System.out.println (new SimpleDateFormat ( "EEEE" ).format ( new Date() )) ;
		
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(newCal.getTime());  
		//System.out.println(newCal.getW);
		System.out.println(newCal.get(Calendar.DAY_OF_WEEK));
		
		if (week != null) {
			model.setViewName("/food");
			model.addObject("menu", menuWeekService.findWeekById(week));
			model.addObject("admin", true);
			model.addObject("listFood", foodService.getAllFood());
		} else {
			model.setViewName("/week-list");
			model.addObject("weekList", menuWeekService.getAllmenu());
		}
		
		return model;
	}
    
    @RequestMapping(value = "/week-list", method = RequestMethod.POST)
    public String weekListPost(@ModelAttribute("menu") MenuWeek menu, BindingResult result, Model model) {    	      
        menuWeekService.update(menu);
        model.addAttribute("menu", menuWeekService.getAllmenu());
        return "redirect:/menu";
    }
    
    @RequestMapping(value = "/order-food", method = RequestMethod.POST)
    public String orderFoodPost(@ModelAttribute("menu") MenuWeek menu, BindingResult result, Model model) {    	      
        menuWeekService.update(menu);
        model.addAttribute("menu", menuWeekService.getAllmenu());
        return "redirect:/menu";
    }
    

}
