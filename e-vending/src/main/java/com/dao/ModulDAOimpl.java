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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.model.CollectionModule;
import com.model.Modul;

/**
 *
 * @author mishka
 */
@Service("modulDAO")
@SuppressWarnings("unchecked")
public class ModulDAOimpl extends HibernateDaoSupport implements ModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public List<Modul> getAllModul() {
		return (List<Modul>) getHibernateTemplate().find("from Modul");
	}

	public void saveOrUpdate(Modul entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public Modul getModul(String idDevice) {
		List<Modul> modul = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where id_device = :id", "id", idDevice);

		if (modul.size() > 0) {
			return modul.get(0);
		} else {
			return null;
		}
	}

	/**
	 * установка факта инкассации в указанное время
	 */
	@Override
	public void setCollection(Calendar timeStamp, double plan, double fakt) {
		CollectionModule collection = new CollectionModule(timeStamp, plan, fakt);
		getHibernateTemplate().save(collection);
	}

}
