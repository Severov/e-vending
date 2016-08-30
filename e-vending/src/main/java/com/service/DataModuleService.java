package com.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.DataModuleDAO;
import com.model.DataModule;

@Service("dataModuleService")
public class DataModuleService extends HibernateDaoSupport implements DataModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(DataModule entity) {
		getHibernateTemplate().save(entity);
	}

}
