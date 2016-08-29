package com.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CashCoinDAO;
import com.model.CashCoin;

@Service("cashCoinService")
public class CashCoinService extends HibernateDaoSupport implements CashCoinDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(CashCoin entity) {
		getHibernateTemplate().save(entity);
	}

}
