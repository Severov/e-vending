package com.service;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CommandToModuleDAO;
import com.model.CommandToModule;

@Service("commandToModuleService")
public class CommandToModuleService extends HibernateDaoSupport implements CommandToModuleDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void save(CommandToModule entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void deleteAll(Set<CommandToModule> entity) {
		getHibernateTemplate().deleteAll(entity);
	}

}
