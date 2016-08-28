package com.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CashNotReceptionDAO;
import com.model.CashNotReception;

@Service("cashNotReceptionService")
public class CashNotReceptionService extends HibernateDaoSupport implements CashNotReceptionDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(CashNotReception entity) {
		getHibernateTemplate().save(entity);
	}

}
