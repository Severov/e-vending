package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.model.Company;
import com.model.TempRegModule;
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

	public String getRandomUid(Company company) {
		Random r = new Random(System.currentTimeMillis());
		String buf = "";
		for (int i = 0; i < 5; i++) {
			buf += (r.nextInt(9));
		}

		getHibernateTemplate().save(new TempRegModule(company, buf));
		return buf;
	}

	public void save(User user) {
		getHibernateTemplate().saveOrUpdate(user);
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
