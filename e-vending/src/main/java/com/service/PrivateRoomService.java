package com.service;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.PrivateRoomDAO;

@Service("privateRoomService")
public class PrivateRoomService extends HibernateDaoSupport implements PrivateRoomDAO {

	@Autowired
	private MySQLQuery mySQLQuery;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<?> getMainTable() {
		SQLQuery result = (SQLQuery) getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(mySQLQuery.getSQL("tableRoom.sql")).setParameter("endDay", getEndOfDay());
		result.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return result.list();
	}

	// *** EXTRA FUNCTION ***

	private Calendar getStartOfDay() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar;
	}

	private Calendar getEndOfDay() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 23, 59, 59);
		return calendar;
	}
}
