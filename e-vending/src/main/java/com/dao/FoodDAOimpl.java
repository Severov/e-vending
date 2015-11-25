package com.dao;

import java.util.ArrayList;
import java.util.List;

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

	public Food findByName(String food) {

		List<Food> findFood = new ArrayList<Food>();

		findFood = sessionFactory.getCurrentSession().createQuery("from Food where name=?").setParameter(0, food).list();

		if (findFood.size() > 0) {
			return findFood.get(0);
		} else {
			return null;
		}

	}

	public Food findById(Integer id) {
		List<Food> findFood = new ArrayList<Food>();

		findFood = sessionFactory.getCurrentSession().createQuery("from Food where id=?").setParameter(0, id).list();

		if (findFood.size() > 0) {
			return findFood.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Food> getAllFood() {
		List<Food> findFood = new ArrayList<Food>();

		findFood = sessionFactory.getCurrentSession().createQuery("from Food").list();

		return findFood;
	}

}
