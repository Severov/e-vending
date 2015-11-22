package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.model.Food;
import com.model.Realization;

@Service("foodDAOimpl")
public class FoodDAOimpl implements FoodDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

	@Override
	public void save(Food food) {
		sessionFactory.getCurrentSession().save(food);
	}
	
	 public Food findByName(String food) {

	        List<Food> findFood = new ArrayList<Food>();

	        findFood = sessionFactory.getCurrentSession().createQuery("from Food where name=? and id='1'")
	                .setParameter(0, food).list();

	        if (findFood.size() > 0) {
	            return findFood.get(0);
	        } else {
	            return null;
	        }

	    }
    
    
}
