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

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.ModuleDAO;
import com.model.CollectionModule;
import com.model.Company;
import com.model.Modul;
import com.model.TempCollection;

/**
 *
 * @author mishka
 */
@Service("modulService")
@SuppressWarnings("unchecked")
public class ModulService extends HibernateDaoSupport implements ModuleDAO {

	@Autowired
	private MySQLQuery mySQLQuery;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public List<Modul> getAllModul() {
		return (List<Modul>) getHibernateTemplate().find("from Modul");
	}

	public Modul test(String id) {
		// return context.getContextPath().toString();
		return (Modul) getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(mySQLQuery.getSQL("testScript.sql")).list().get(0);
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

	public Modul getModul(String idDevice) {
		List<Modul> modul = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where id_device = :id", "id", idDevice);

		if (modul.size() > 0) {
			return modul.get(0);
		} else {
			return null;
		}
	}

	public void setTempCollection(Modul modul, Double plan, Double fakt) {
		getHibernateTemplate().save(new TempCollection(plan, fakt));
	}

	/**
	 * установка факта инкассации
	 */
	@Override
	public void setCollection(Modul modul, Double plan, Double fakt) {
		CollectionModule collection = new CollectionModule(modul, Calendar.getInstance(), plan, fakt);
		getHibernateTemplate().save(collection);
	}

	@Override
	public void setCollection(Modul modul) {
		TempCollection buf = getLastTempCollection(modul);
		Double plan = 0.0;
		Double fakt = 0.0;

		if (buf == null) {
			plan = getSumm(modul);
			fakt = plan;
		} else {
			plan = buf.getPlan();
			fakt = buf.getFakt();
			deleteAllTempCollection(modul);
		}

		setCollection(modul, plan, fakt);
	}

	@Override
	public TempCollection getLastTempCollection(Modul modul) {
		SessionFactory session = getHibernateTemplate().getSessionFactory();
		List<TempCollection> buf = session.getCurrentSession().createQuery("from TempCollection where modul_id = :id order by temp_id DESC")
			.setParameter("id", modul.getId()).setMaxResults(1).list();

		if (!buf.isEmpty()) {
			return buf.get(0);
		}

		return null;
	}

	@Override
	public Double getSumm(Modul modul) {
		// TODO Обязательно реализовать данный метод
		return 777.0;
	}

	@Override
	public void deleteAllTempCollection(Modul modul) {
		SessionFactory session = getHibernateTemplate().getSessionFactory();
		String deleteQuery = "delete from TempCollection where modul_id= :id";
		session.getCurrentSession().createQuery(deleteQuery).setParameter("id", modul.getId()).executeUpdate();
	}

	@Override
	public Modul getModulByUin(String uin) {
		List<Modul> modul = (List<Modul>) getHibernateTemplate().findByNamedParam("from Modul where uin = :id", "id", uin);

		if (modul.size() > 0) {
			return modul.get(0);
		} else {
			return null;
		}
	}

}
