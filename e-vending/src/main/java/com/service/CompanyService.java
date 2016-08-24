package com.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.dao.CompanyDAO;
import com.model.Company;

@Service("companyService")
public class CompanyService extends HibernateDaoSupport implements CompanyDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Company getCompany(Integer id) {

		List<Company> company = (List<Company>) getHibernateTemplate().findByNamedParam("from Company where id = :id", "id", id);

		if (company.size() > 0) {
			return company.get(0);
		} else {
			return null;
		}
	}

}
