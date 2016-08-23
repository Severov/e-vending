/**
 * 
 */
package com.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.model.DataDoor;

/**
 * @author mishka
 *
 */
@Service("dataDoorDAO")
public class DataDoorDAOimpl extends HibernateDaoSupport implements DataDoorDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(DataDoor entity) {
		getHibernateTemplate().save(entity);

	}

}
