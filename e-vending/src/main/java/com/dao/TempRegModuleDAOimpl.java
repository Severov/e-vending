package com.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.model.TempRegModule;

@Service("tempRegModuleDAO")
public class TempRegModuleDAOimpl extends HibernateDaoSupport implements TempRegModulDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TempRegModule findBySecretCode(String secret) {

		// Отнимен от текущей даты 5-ть минут
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);

		String[] param = { "secret", "timeStamp" };
		Object[] value = { secret, calendar };

		List<TempRegModule> tmp = (List<TempRegModule>) getHibernateTemplate()
				.findByNamedParam("from TempRegModule where secret = :secret AND timeStamp > :timeStamp", param, value);

		if (tmp.size() > 0) {
			return tmp.get(0);
		} else {
			return null;
		}
	}

}
