package com.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.ChartsDAO;
import com.model.Modul;

@Service("chartsService")
public class ChartsService extends HibernateDaoSupport implements ChartsDAO {
	
	@Autowired
	private MySQLQuery mySQLQuery;
	
	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<?> getChartBond(Modul modul) {
		SQLQuery query = (SQLQuery) getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(mySQLQuery.getSQL("bondChart.sql"));

		query.setParameter("modul_id", modul.getId());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<?> getChartCollection(Modul modul) {
		SQLQuery query = (SQLQuery) getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(mySQLQuery.getSQL("collectionChart.sql"));

		query.setParameter("modul_id", modul.getId());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

}
