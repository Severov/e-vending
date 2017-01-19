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
package com.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.ModuleDAO;
import com.model.CommandToModule;
import com.model.Company;
import com.model.Modul;

/**
 *
 * @author mishka
 */
@Service("modulService")
@SuppressWarnings("unchecked")
public class ModulService extends HibernateDaoSupport implements ModuleDAO {
	
	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public List<Modul> getAllModul() {
		return (List<Modul>) getHibernateTemplate().find("from Modul");
	}

	@Override
	public Modul findModul(String uin, Company company) {
		String[] param = { "company", "uin" };
		Object[] value = { company.getId(), uin };

		List<Modul> modul = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where company_id = :company AND uin = :uin", param, value);
		if (modul.isEmpty()) {
			return null;
		}

		return modul.get(0);
	}

	@Override
	public void save(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteAll(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) return;
		
		getHibernateTemplate().deleteAll(collection);
	}

	@Override
	public void saveAll(Collection<?> collection) {
		for (Object entity : collection) {
			getHibernateTemplate().save(entity);
		}
	}

	@Override
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@Override
	public void update(Object entity){
		getHibernateTemplate().update(entity);
	}

	public Modul getModul(String idDevice) {	
		
		List<Modul> modul = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where id_device = :id", "id", idDevice);

		if (modul.size() > 0) {
			return modul.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Modul getModulByUin(String uin) {
		if(uin == null){
			return null;
		}
				
		List<Modul> modulList = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where uin = :id", "id", uin);

		if (modulList.size() > 0) {
			return modulList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Возвращает накопившиеся команды модуля
	 */
	@Override
	public String getCommandString(Modul modul){
		
		List<CommandToModule> command = (List<CommandToModule>) getHibernateTemplate().findByNamedParam("from CommandToModule where modul_id = :id", "id", modul.getId());

		if (command.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (CommandToModule entity : command) {
			builder.append(" ");
			builder.append(entity.getCommand());
		}

		return builder.toString();
	}
	
	@Override
	public void deleteAllCommand(Modul modul){
		SessionFactory session = getHibernateTemplate().getSessionFactory();
		String deleteQuery = "delete from CommandToModule where modul_id= :id";
		session.getCurrentSession().createQuery(deleteQuery).setParameter("id", modul.getId()).executeUpdate();
	}
	
	
}
