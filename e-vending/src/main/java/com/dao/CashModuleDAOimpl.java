package com.dao;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.model.CashModule;
import com.model.Modul;

@Service("cashModuleDAO")
public class CashModuleDAOimpl extends HibernateDaoSupport implements CashModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void saveCashModule(Modul modul, Calendar timeStamp, Integer cash, Integer bond, Integer sell, Integer bs) {
		CashModule tmp = new CashModule(modul, cash, bond, sell, bs);
		getHibernateTemplate().save(tmp);
	}

	@Override
	public void saveCashModule(CashModule entity) {
		getHibernateTemplate().save(entity);
	}

}
