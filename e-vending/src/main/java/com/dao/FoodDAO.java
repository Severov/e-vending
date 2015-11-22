package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.Food;
import com.model.User;

public interface FoodDAO {

	@Transactional(readOnly = false)
	void save(Food food);
	
	@Transactional(readOnly = true)
	Food findByName(String food);
}
