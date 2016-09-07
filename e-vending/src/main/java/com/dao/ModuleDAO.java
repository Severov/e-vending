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

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Company;
import com.model.Modul;
import com.model.TempCollection;

/**
 *
 * @author mishka
 * @param <T>
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

	@Transactional(readOnly = true)
	Modul getModulByUin(String uin);

	@Transactional(readOnly = true)
	Modul findModul(String uin, Company company);

	@Transactional(readOnly = false)
	void saveOrUpdate(Object entity);

	@Transactional(readOnly = false)
	void save(Object entity);

	@Transactional(readOnly = false)
	void delete(Object entity);

	@Transactional(readOnly = false)
	void deleteAll(Collection<?> collection);

	@Transactional(readOnly = false)
	void saveAll(Collection<?> collection);

	@Transactional(readOnly = false)
	void setCollection(Modul modul, Double plan, Double fakt);

	@Transactional(readOnly = false)
	void setCollection(Modul modul);

	@Transactional(readOnly = false)
	void setTempCollection(Modul modul, Double plan, Double fakt);

	@Transactional(readOnly = true)
	TempCollection getLastTempCollection(Modul modul);

	@Transactional(readOnly = true)
	public Double getSumm(Modul modul);

	@Transactional(readOnly = false)
	public void deleteAllTempCollection(Modul modul);
}
