package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.Realization;

public interface RealizationDAO {

	@Transactional(readOnly = false)
	void save(Realization real);
}
