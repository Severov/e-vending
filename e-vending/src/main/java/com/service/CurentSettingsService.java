package com.service;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CurentSettingsModuleDAO;
import com.model.CurentSettingsModule;

@Service("curentSettingsService")
public class CurentSettingsService extends HibernateDaoSupport implements CurentSettingsModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void deleteAll(Set<CurentSettingsModule> settings) {
		getHibernateTemplate().deleteAll(settings);
	}

	@Override
	public void saveAll(Set<CurentSettingsModule> settings) {
		for (CurentSettingsModule curentSettingsModule : settings) {
			getHibernateTemplate().save(curentSettingsModule);
		}
	}

}
