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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CashModuleDAO;
import com.dao.ModuleDAO;
import com.dao.PrivateRoomDAO;
import com.dao.TempRegModulDAO;
import com.dao.UserDAO;
import com.model.CommandToModule;
import com.model.Modul;
import com.model.ResetKup;
import com.model.TempCollection;
import com.model.TempRegModule;

/**
 *
 * @author mishka
 */
@RestController
@RequestMapping(value = "/private/ws")
public class PrivateRoomController {
	
	private static final Logger logger = Logger.getLogger(PrivateRoomController.class);

	@Autowired
	private PrivateRoomDAO privateRoomService;
	
	@Autowired
	private TempRegModulDAO tempRegModulService;
	
	@Autowired
	private UserDAO		userService;

	@Autowired
	private ModuleDAO modulService;
	
	@Autowired
	private CashModuleDAO cashModulService;

	@RequestMapping(value = "/table", method = RequestMethod.GET)
	private Map<Object, Object> getTable(@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sort", required = false) String sort) {
		List<?> data = privateRoomService.getMainTable(order, sort);
		
		ArrayList<Object> footer = new ArrayList<>();
		footer.add(new HashMap<String, String>() { // начинается говнокод (
			{
			
				Integer sell = 0;
				Integer day_summ = 0;
				Integer count_bond_day = 0;
				Integer max_cash = 0;
				
				for (int i = 0; i < data.size(); i ++){
					sell += Integer.parseInt(((Map<String, Object>) data.get(i)).get("max_sell").toString());
					day_summ += Integer.parseInt(((Map<String, Object>) data.get(i)).get("day_summ").toString());
					count_bond_day += Integer.parseInt(((Map<String, Object>) data.get(i)).get("count_bond_day").toString());
					max_cash += Integer.parseInt(((Map<String, Object>) data.get(i)).get("max_cash").toString());
				}
				
				put("place", "<b>ИТОГО: </b>");
				put("max_sell", "<b>" + sell.toString() + "</b>");
				put("day_summ", "<b>" + day_summ.toString() + "</b>");
				put("count_bond_day", "<b>" + count_bond_day.toString() + "</b>");
				put("max_cash", "<b>" + max_cash.toString() + "</b>");
				
			}

		});

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("rows", data);
		map.put("total", data.size());
		map.put("footer", footer);

		return map;
	}

	/**
	 * Сброс статистики купюрника
	 * @param uin - уин модуля
	 * @return
	 */
	@RequestMapping(value = "/resetKup", method = RequestMethod.GET)
	private Boolean resetKup(@RequestParam(value = "uin", required = false) String uin) {
		Modul modul = modulService.getModulByUin(uin);
		if (modul == null) return false;

		modulService.save(new ResetKup(modul, Calendar.getInstance()));
		return true;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	private Double test(@RequestParam(value = "uin", required = false) String uin){
		Modul modul = modulService.getModulByUin(uin);
		if (modul == null) return null;
		
		return cashModulService.getSummCollection(modul);
	}
	
	@RequestMapping(value = "/setCollection", method = RequestMethod.GET)
	private Boolean setCollection(@RequestParam(value = "uin", required = false) String uin,
			@RequestParam(value = "plan", required = false) Double plan,
			@RequestParam(value = "fakt", required = false) Double fakt) {
		Modul modul = modulService.getModulByUin(uin);
		if (modul == null)
			return null;

		CommandToModule comand = new CommandToModule(modul, "collect");
		TempCollection tmpCollection = new TempCollection(modul, plan, fakt);
		
		modulService.save(comand);
		modulService.save(tmpCollection);
		
		return true;
	}
		
	/**
	 * Сохраняет в БД и возвращает секретный код для регистрации нового модуля
	 * @return
	 */
	@RequestMapping(value = "/registerModul", method = RequestMethod.GET)
	private String registerModul(){
		String code = tempRegModulService.generateSecretCode(5);
		modulService.save(new TempRegModule(userService.getAuthenticationUser().getCompany(), code));
		return code;	
	}
	
	@RequestMapping(value = "/saveInfoModul", method = RequestMethod.GET)
	private Boolean saveInfoModul(@RequestParam(value = "uin", required = false) String uin,
			@RequestParam(value = "place", defaultValue="", required = false) String place,
			@RequestParam(value = "trademark", defaultValue = "", required = false) String trademark) {

		Modul modul = modulService.getModulByUin(uin);
		if (modul == null) return false;

		modul.setPlace(place);
		modul.setTrademark(trademark);

		modulService.saveOrUpdate(modul);
		return true;
	}
	
	
	@RequestMapping(value = "/deleteModul", method = RequestMethod.GET)
	private Boolean deleteModul(@RequestParam(value = "uin", required = false) String uin){
		Modul modul = modulService.getModulByUin(uin);
		if (modul == null) return false;
		
		modul.setActive(false);
		modul.setCompany(null);
		modul.setTrademark("");
		modul.setPlace("");
		modulService.saveOrUpdate(modul);
		
		return true;
	}

}
