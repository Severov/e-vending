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
import com.dao.OrderDAO;
import com.dao.OrderFoodDAO;
import com.dao.RealizationDAO;
import com.dao.UserDao;
import com.editor.FoodEditor;
import com.model.Food;
import com.model.MenuWeek;
import com.model.Order;
import com.model.OrderFood;
import com.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "foodEditor")
	private FoodEditor foodEditor;

	@Resource(name = "OrderFoodService")
	private OrderFoodDAO orderFoodService;

	@Resource(name = "orderService")
	private OrderDAO orderService;

	@ModelAttribute("listFood")
	public List<Food> getlist() {
		return foodService.getAllFood();
	}
	
	@ModelAttribute("orderFood")
	public OrderFood getOrderSet() {
		return new OrderFood();
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Food.class, foodEditor);
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String add_module(Model model) {
		model.addAttribute("menu", menuWeekService.getAllmenu());
		model.addAttribute("href", "week-list");
		return "/menu";
	}

	// Клиент формирует меню
	@RequestMapping(value = "/food-list", method = RequestMethod.GET)
	public String foodListGET(@RequestParam(value = "week", required = false, defaultValue = "0") int week, Model model) {

		if (week != 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - week));		
			OrderFood orderNow = orderFoodService.findByIdAndDate(16, cal.getTime());
			
			MenuWeek currennMenu = menuWeekService.findWeekById(week);

			Set<Order> ord = new HashSet<>();
			if (orderNow.getOrder().size() == 0) {
				orderNow.setUser(userDao.findByUserName("Mishka"));
				orderNow.setId(-1);
				orderNow.setDate(Calendar.getInstance().getTime());

				for (Food food : currennMenu.getFood()) {
					Order buf = new Order();
					buf.setCount(0.0);
					buf.setFood(food);
					ord.add(buf);
				}
				
				orderNow.setOrder(ord);
			} else {
				for (Food food : currennMenu.getFood()) {
					boolean flag = false;
					for (Order order : orderNow.getOrder()) {
						if (food.equals(order)) {
							flag = true;
							break;
						}
					}
						
					if(!flag){
						Order buf = new Order();
						buf.setCount(0.0);
						buf.setFood(food);
						ord.add(buf);
					}
				}
			}

			model.addAttribute("orderFood", orderNow);
			model.addAttribute("description", "Это твое меню");
			model.addAttribute("listFood", menuWeekService.findWeekById(week).getFood());
			model.addAttribute("admin", true);
			return "/food-user";
		} else {
			// Сформируем меню пользователя
			List<MenuWeek> menuList = menuWeekService.getAllmenu();
			for (MenuWeek menu : menuList) {
				menu.setFood(new ArrayList<Food>()); // очистим меню

				// Сформируем его личное меню
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - menu.getWeek()));
				for (Order order : orderFoodService.findByIdAndDate(16, cal.getTime()).getOrder()) {
					menu.getFood().add(order.getFood());
				}
			}

			model.addAttribute("href", "food-list");
			model.addAttribute("menu", menuList);
			return "/menu";
		}
	}
	
	@RequestMapping(value = "/food-list", method = RequestMethod.POST)
	public String foodListPOST(@ModelAttribute("orderFood") OrderFood orderFood, BindingResult result, Model model) {
		 model.addAttribute("menu", menuWeekService.getAllmenu());
		return "redirect:/food-list";
	}

	// ---------------------------------------------------------------
	// Клиент формирует меню
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@Transactional
	public String testGET(Model model) {

		// List<OrderFood> m = orderService.getAllOrder();
		User u = userDao.findByUserName("Mishka");
		int m = u.getOrder().size();
		model.addAttribute("user", u);
		// model.addAttribute("order", m);
		return "/test";
	}

	// Клиент формирует меню
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String testPOST(@ModelAttribute("user") User user, BindingResult result, Model model) {
		// model.addAttribute("menu", menuWeekService.getAllmenu());
		return "redirect:/test";
	}
	// ----------------------------------------------------------------
	// @RequestMapping(value = "/food-list", method = RequestMethod.GET)
	// public String foodListGet(Model model) {
	// model.addAttribute("foodList", menuWeekService.getAllmenu());
	// return "/food-list";
	// }

	@RequestMapping(value = "/week-list88", method = RequestMethod.GET)
	public ModelAndView weekListFoodGet(@RequestParam(value = "week", required = false, defaultValue = "0") int week) {
		ModelAndView model = new ModelAndView();
		// System.out.println (new SimpleDateFormat ( "EEEE" ).format ( new
		// Date() )) ;

		Calendar newCal = new GregorianCalendar();
		newCal.setTime(newCal.getTime());
		// System.out.println(newCal.getW);
		System.out.println(newCal.get(Calendar.DAY_OF_WEEK));

		if (week != 0) {
			model.setViewName("/food");
			model.addObject("menu", menuWeekService.findWeekById(week));
			model.addObject("admin", true);
			model.addObject("listFood", foodService.getAllFood());
		} else {
			model.setViewName("/week-list");
			model.addObject("href", "week-list");
			model.addObject("weekList", menuWeekService.getAllmenu());
		}

		return model;
	}

	// Клиент формирует себе меню
	@RequestMapping(value = "/week-list", method = RequestMethod.GET)
	public ModelAndView dayListFoodGet(@RequestParam(value = "week", required = false, defaultValue = "0") int week) {
		ModelAndView model = new ModelAndView();
		// System.out.println (new SimpleDateFormat ( "EEEE" ).format ( new
		// Date() )) ;

		Calendar newCal = new GregorianCalendar();
		newCal.setTime(newCal.getTime());
		// System.out.println(newCal.getW);
		System.out.println(newCal.get(Calendar.DAY_OF_WEEK));

		if (week != 0) {
			model.setViewName("/food");
			model.addObject("menu", menuWeekService.findWeekById(week));
			model.addObject("admin", true);
			model.addObject("listFood", foodService.getAllFood());
		} else {
			model.setViewName("/week-list");
			model.addObject("href", "week-list");
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
