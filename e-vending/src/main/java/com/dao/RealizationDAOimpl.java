package com.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.model.Realization;

@Service("realizationDAOimpl")
public class RealizationDAOimpl implements RealizationDAO {
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Realization real) {
		sessionFactory.getCurrentSession().save(real);		
	}
	   
	   

}
