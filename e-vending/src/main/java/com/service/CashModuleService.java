package com.service;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CashModuleDAO;
import com.model.CashModule;
import com.model.CollectionModule;
import com.model.Modul;
import com.model.TempCollection;

@Service("cashModuleService")
public class CashModuleService extends HibernateDaoSupport implements CashModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Autowired
	private MySQLQuery mySQLQuery;

	@Override
	public void saveCashModule(CashModule entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * Возвращает текущую сумму в купюроприёмнике
	 * 
	 */
	@Override
	public Double getSummCollection(Modul modul) {
		
		SQLQuery query =  getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(mySQLQuery.getSQL("getSummCollection.sql"));
		query.setParameter("modul_id", modul.getId());
		query.addScalar("max_cash", DoubleType.INSTANCE);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Double>> result = query.list();
		
		if (result.size() > 0){
			return  result.get(0).get("max_cash");
		}
		
		return 0.0;
	}
	
	/**
	 * Возвращает текущее количество купюр в купюроприёмнике
	 */
	@Override
	public Integer getCountBond(Modul modul){
		SQLQuery query =  getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(mySQLQuery.getSQL("getCountBond.sql"));
		query.setParameter("modul_id", modul.getId());
		query.addScalar("bs", IntegerType.INSTANCE);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Integer>> result = query.list();
		
		if (result.size() > 0){
			return  result.get(0).get("bs");
		}
		
		return 0;	
	}
	
	@Override
	public void deleteAllTempCollection(Modul modul) {
		SessionFactory session = getHibernateTemplate().getSessionFactory();
		String deleteQuery = "delete from TempCollection where modul_id= :id";
		session.getCurrentSession().createQuery(deleteQuery).setParameter("id", modul.getId()).executeUpdate();
	}
	
	/**
	 * установка факта инкассации
	 */
	@Override
	public void setCollection(Modul modul, Double plan, Double fakt) {
		CollectionModule collection = new CollectionModule(modul, plan, fakt, getCountBond(modul));
		getHibernateTemplate().save(collection);
	}

	@Override
	public void setCollection(Modul modul) {
		TempCollection buf = getLastTempCollection(modul);
		Double plan = 0.0;
		Double fakt = 0.0;

		if (buf == null) {
			plan = getSummCollection(modul);
			fakt = plan;
		} else {
			plan = buf.getPlan();
			fakt = buf.getFakt();
			deleteAllTempCollection(modul);
		}

		setCollection(modul, plan, fakt);
	}
	
	/**
	 * Возвращает крайние значения план/факт для инкассаций сделанных через сайт
	 */
	@Override
	public TempCollection getLastTempCollection(Modul modul) {
		SessionFactory session = getHibernateTemplate().getSessionFactory();
		
		@SuppressWarnings("unchecked")
		List<TempCollection> buf = session.getCurrentSession().createQuery("from TempCollection where modul_id = :id order by temp_id DESC")
			.setParameter("id", modul.getId()).setMaxResults(1).list();

		if (!buf.isEmpty()) {
			return buf.get(0);
		}

		return null;
	}

}
