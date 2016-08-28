package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.CashNotReception;

public interface CashNotReceptionDAO {

	@Transactional(readOnly = false)
	void save(CashNotReception entity);
}
