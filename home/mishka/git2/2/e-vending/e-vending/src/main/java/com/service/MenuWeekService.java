package com.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.dao.MenuWeekDAO;
import com.model.Food;
import com.model.MenuWeek;

@Service("menuWeekService")
public class MenuWeekService implements MenuWeekDAO{

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<MenuWeek> getAllmenu() {
		return sessionFactory.getCurrentSession().createQuery("from MenuWeek").list();
	}

	@Override
	public void save(MenuWeek menuWeek) {
		sessionFactory.getCurrentSession().save(menuWeek);		
	}

	@Override
	public MenuWeek findWeekById(int id) {
		List<MenuWeek> week = new ArrayList<MenuWeek>();
		
		week = sessionFactory.getCurrentSession().createQuery("from MenuWeek where id=?").setParameter(0, id).list();

		if (week.size() > 0) {
			return week.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void update(MenuWeek week) {
		sessionFactory.getCurrentSession().update(week);		
	}
	
	

}
