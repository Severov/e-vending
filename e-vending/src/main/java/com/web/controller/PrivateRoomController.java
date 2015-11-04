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

import com.dao.ModuleDAO;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
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
    
    @RequestMapping(value = "private_room", method = RequestMethod.GET)
    public String private_room() {
        return "private_room";
    }

    @RequestMapping(value = "room/module", method = RequestMethod.GET)
    public String add_module(Model model) {
        model.addAttribute("module_list",  module.getAllModul());
        return "room/module";
    }

}