package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.Food;

public interface FoodDAO {

	@Transactional(readOnly = false)
	void save(Food food);
}
