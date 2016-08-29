package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.ErrorModule;

public interface ErrorModuleDAO {

	@Transactional(readOnly = false)
	void save(ErrorModule entity);
}
