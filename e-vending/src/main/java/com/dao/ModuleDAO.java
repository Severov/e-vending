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
package com.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Modul;

/**
 *
 * @author mishka
 */
public interface ModuleDAO {

	@Transactional(readOnly = true)
	List<Modul> getAllModul();

	/**
	 * Возвращает модуль по его id_device
	 * 
	 * @param idDevice
	 * @return
	 */
	@Transactional(readOnly = true)
	Modul getModul(String idDevice);

	@Transactional(readOnly = false)
	void saveOrUpdate(Modul entity);

	@Transactional(readOnly = false)
	void setCollection(Calendar calendar, double plan, double fakt);
}
