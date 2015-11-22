package com.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.model.Food;

@Service("foodDAOimpl")
public class FoodDAOimpl implements FoodDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

	@Override
	public void save(Food food) {
		sessionFactory.getCurrentSession().save(food);
		
	}
    
    
}
