package com.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.ErrorModuleDAO;
import com.model.ErrorModule;

@Service("errorModuleService")
public class ErrorModuleService extends HibernateDaoSupport implements ErrorModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(ErrorModule entity) {
		getHibernateTemplate().save(entity);
	}

}
