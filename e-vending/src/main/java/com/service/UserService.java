package com.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.model.Company;
import com.model.User;

@Service("userService")
public class UserService extends HibernateDaoSupport implements UserDAO {

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = (List<User>) getHibernateTemplate().findByNamedParam("from User where username = :username", "username", username);

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public User getUserByApiKey(String apiKey) {

		List<User> users = new ArrayList<User>();

		users = (List<User>) getHibernateTemplate().findByNamedParam("from User where apiKey = :apiKey", "apiKey", apiKey);

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	public void save(User user) {
		getHibernateTemplate().saveOrUpdate(user);
	}
	
	public void save(Company company) {
		getHibernateTemplate().save(company);
	}

	@Override
	public User getAuthenticationUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}

		return (User) authentication.getPrincipal();
	}

}
