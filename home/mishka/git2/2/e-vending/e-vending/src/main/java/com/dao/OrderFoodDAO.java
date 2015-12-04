package com.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.OrderFood;

public interface OrderFoodDAO {
	
	@Transactional(readOnly = true)
	List<OrderFood> getAllOrder();
	
	@Transactional(readOnly = true)
	OrderFood findById(int id);
	
	@Transactional(readOnly = true)
	OrderFood findByIdAndDate(int user, Date date);
	
	@Transactional(readOnly = false)
	void saveOrUpdate(OrderFood order);
}
