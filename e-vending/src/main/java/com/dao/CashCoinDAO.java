package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.CashCoin;

public interface CashCoinDAO {

	@Transactional(readOnly = false)
	void save(CashCoin entity);
}
