package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.Order;

public interface OrderDAO {

	@Transactional(readOnly = false)
	void saveOrUpdate(Order order);
}
