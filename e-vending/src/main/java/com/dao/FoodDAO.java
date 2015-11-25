package com.dao;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Food;

public interface FoodDAO {

	@Transactional(readOnly = false)
	void save(Food food);
	
	@Transactional(readOnly = true)
	Food findById(Integer id);
	
	@Transactional(readOnly = true)
	Food findByName(String food);
	
	@Transactional(readOnly = true)
	List<Food> getAllFood();
	
}
