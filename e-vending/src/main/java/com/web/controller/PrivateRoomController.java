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
import com.dao.ModuleDAO;
import com.dao.RealizationDAO;
import com.dao.UserDao;
import com.model.Food;
import com.model.Realization;
import com.model.User;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author mishka
 */
@Controller
public class PrivateRoomController {

    @Resource(name = "modulDAOimpl")
    private ModuleDAO module;
    
    @Resource(name = "foodDAOimpl")
    private FoodDAO food;
    
    @Resource(name = "realizationDAOimpl")
    private RealizationDAO real;
    
	@Resource(name="userDaoImpl")
	private UserDao userDao;
    
	@Transactional
    @RequestMapping(value = "private_room", method = RequestMethod.GET)
    public String private_room() {
    	
    	Food nf = new Food();
    	nf.setDescription("Борщевая заправка )");
    	nf.setName("Украинский борщ");
    	nf.setPrice(8);
    	
    	User u = new User();
    	u.setEmail("sf@2222");
    	u.setEnabled(true);
    	u.setFullname("8787aaa");
    	u.setPassword("123456");
    	u.setUsername("Mishka");
		
    	Realization r = new Realization();
    	r.setFood(nf);
    	r.setPrice(9);
    	r.setUser(u);
    	
    	//userDao.saveUser(u);
    	//food.save(nf);
    	//real.save(r);
    	
    	Food tt = food.findByName("Украинский борщ");
    	List<Realization> st = tt.getRealization();
    	
    	for( Realization entry : st ){
    		System.out.print( entry.getUser().getId() );
    	}
    	
    	System.out.println(tt.getDescription());
    	
        return "private_room";
    }

    @RequestMapping(value = "room/module", method = RequestMethod.GET)
    public String add_module(Model model) {
        model.addAttribute("module_list",  module.getAllModul());
        return "room/module";
    }

}
