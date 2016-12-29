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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.PrivateRoomDAO;


/**
 *
 * @author mishka
 */
@RestController
@RequestMapping(value = "/private/ws")
public class PrivateRoomController {
	
	@Autowired
	private PrivateRoomDAO privateRoomService;

	@RequestMapping(value = "/table", method = RequestMethod.GET)
	private Map<Object, Object> getTable() {
		List<?> data =  privateRoomService.getMainTable();

		ArrayList<Object> footer = new ArrayList<>();
		footer.add(new HashMap<String, String>() {
			{
				put("place", "<b>ИТОГО</b>");
				put("max_sell", "<b>" + ((Map<String,Object>)data.get(0)).get("sum_max_sell") + "</b>");
			}

		});

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("rows",   data);
		map.put("total",  data.size());
		map.put("footer", footer);

		return map;
	}

}
