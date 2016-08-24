package com.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.DataDoorDAO;
import com.model.DataDoor;

/**
 * @author mishka
 *
 */
@Service("dataDoorService")
public class DataDoorService extends HibernateDaoSupport implements DataDoorDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(DataDoor entity) {
		getHibernateTemplate().save(entity);

	}

}
