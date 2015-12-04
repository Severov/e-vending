package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.model.Food;
import com.model.MenuWeek;
import com.model.Order;
import com.model.OrderFood;

@Service("OrderFoodService")
public class OrderFoodDAOImpl implements OrderFoodDAO {
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public List<OrderFood> getAllOrder() {
		List<OrderFood> order = new ArrayList<OrderFood>();
		order = sessionFactory.getCurrentSession().createQuery("from OrderFood").list();
		return order;
	}

	@Override
	public OrderFood findById(int id) {
		List<OrderFood> order = new ArrayList<OrderFood>();

		order = sessionFactory.getCurrentSession().createQuery("from OrderFood where id=?").setParameter(0, id).list();

		if (order.size() > 0) {
			return order.get(0);
		} else {
			return null;
		}
	}

	@Override
	public OrderFood findByIdAndDate(int user, Date date) {
		List<OrderFood> order = new ArrayList<OrderFood>();

		order = sessionFactory.getCurrentSession().createQuery("from OrderFood where user_id=? and date=?")
				.setParameter(0, user)
				.setParameter(1, date).list();
		
		if (order.size() > 0) {
			return order.get(0);
		} else {
			return new OrderFood();
		}
	}

	@Override
	public void saveOrUpdate(OrderFood order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
}
