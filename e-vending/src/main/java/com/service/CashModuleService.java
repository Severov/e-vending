package com.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CashModuleDAO;
import com.model.CashModule;
import com.model.Modul;

@Service("cashModuleService")
public class CashModuleService extends HibernateDaoSupport implements CashModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void saveCashModule(CashModule entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * Возвращает текущую сумму в купюроприёмнике
	 * 
	 */
	@Override
	public Integer getSumm(Modul modul) {
		SessionFactory session = getHibernateTemplate().getSessionFactory();

		String sql = "SELECT MAX(IFNULL(cash, 0)) AS `max_cash`, cashModule.modul_id	FROM cashModule	INNER JOIN ( SELECT MAX(timeStamp) AS `t1`, modul_id FROM collectionModule WHERE modul_id = :modul_id GROUP BY modul_id ) AS `tmp` ON cashModule.modul_id = tmp.modul_id AND tmp.t1 <= cashModule.timeStamp GROUP BY cashModule.modul_id";
		List<Object> rez = session.getCurrentSession().createSQLQuery(sql).setLong("modul_id", modul.getId()).list();
		return rez.size();
	}

}
