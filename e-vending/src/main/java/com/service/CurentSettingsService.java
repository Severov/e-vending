package com.service;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CurentSettingsModuleDAO;
import com.model.CurentSettingsModule;
import com.model.Modul;

@Service("curentSettingsService")
public class CurentSettingsService extends HibernateDaoSupport implements CurentSettingsModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void delete(Modul entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void saveAll(Set<CurentSettingsModule> settings) {
		for (CurentSettingsModule curentSettingsModule : settings) {
			getHibernateTemplate().save(curentSettingsModule);
		}
	}

}
