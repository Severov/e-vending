package com.dao;

import com.model.User;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

@Service("userDaoImpl")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
	
	@Autowired
	public void init() {
	    setSessionFactory(sessionFactory);
	}

    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {

        List<User> users = new ArrayList<User>();
        
       // getHibernateTemplate().find(queryString, values)

        users = sessionFactory.getCurrentSession().createQuery("from User where username=?")
                .setParameter(0, username).list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }

    
    public void save(User user) {
    	getHibernateTemplate().save(user);
    }

}
