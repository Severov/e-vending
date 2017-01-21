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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.ModuleDAO;
import com.dao.UserDAO;
import com.model.CommandToModule;
import com.model.CurrentSettingsModule;
import com.model.Modul;
import com.model.User;

/**
 * Обслуживание странички личного кабинета пользователя
 * 
 * @author mishka
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/features/{uin}", method = RequestMethod.GET)
@Scope(value = "prototype")
public class RestController {

	private Modul		modul;
	private User		user;

	@Resource(name = "modulService")
	private ModuleDAO	modulService;

	@Resource(name = "userService")
	private UserDAO		userService;

	/**
	 * Проверка прав доступа пользователя к указанному модулю
	 * 
	 * @param modul
	 *            - запрашиваемый модуль
	 * @return - true если доступ разрешен. false - доступ запрещен
	 */
	private boolean isAccess(String uin) {
		if (!isAccess()) {
			return false;
		}

		this.modul = modulService.findModul(uin, user.getCompany());
		if (modul == null) {
			return false;
		}

		return true;
	}

	private boolean isAccess() {
		user = userService.getAuthenticationUser();
		if (user == null) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/getSettingsModule")
	public CurrentSettingsModule getSettingsModule(@PathVariable("uin") String uin) {
		if (!isAccess(uin)) {
			return null;
		}

		return modul.getCurrentSettings();
	}

	@RequestMapping(value = "/saveUserCommand")
	public String saveUserCommand(@PathVariable("uin") String uin, @RequestParam(value = "userCommand") String userCommand) {
		if (userCommand == null) {
			return null;
		}

		if (!isAccess(uin)) {
			return null;
		}

		modulService.save(new CommandToModule(modul, userCommand));
		return "Успешно";
	}

	@RequestMapping(value = "/getVersion")
	public Modul getVersion(@PathVariable("uin") String uin) {
		if (!isAccess(uin)) {
			return null;
		}

		return modul;
	}

	@RequestMapping(value = "/updateInfoModul")
	public Modul updateInfoModul(@PathVariable("uin") String uin, @RequestParam(value = "trademark") String trademark,
		@RequestParam(value = "place") String place) {
		if (!isAccess(uin)) {
			return null;
		}

		modul.setTrademark(trademark);
		modul.setPlace(place);
		modulService.saveOrUpdate(modul);

		return modul;
	}

	@RequestMapping(value = "/deleteModule")
	public String deleteModul(@PathVariable("uin") String uin) {
		if (!isAccess(uin)) {
			return null;
		}

		modul.setActive(false);
		modul.setActivenum("");
		modul.setCompany(null);
		modul.setTrademark("");
		modul.setPlace("");

		modulService.saveOrUpdate(modul);
		return "ok";
	}

	@RequestMapping(value = "/saveSettingsModule")
	public String saveSettingsModule(@PathVariable("uin") String uin, @RequestParam Map<String, String> allSettings) {
		if (!isAccess(uin)) {
			return "modul not found";
		}

		if (allSettings.containsKey("minutes") && allSettings.containsKey("hours")) {
			CommandToModule entity = new CommandToModule(modul);
			entity.setCommand("time" + allSettings.get("hours") + allSettings.get("minutes"));
			modul.getCommand().add(entity);
		}

		for (String key : allSettings.keySet()) {
			if (key.equals("hours") || key.equals("minutes")) {
				continue;
			}

			CommandToModule entity = new CommandToModule(modul);
			entity.setCommand(key + allSettings.get(key));
			modul.getCommand().add(entity);
		}

		modulService.saveOrUpdate(modul);

		return "ok";
	}

}
