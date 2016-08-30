package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.DataModule;

public interface DataModuleDAO {

	@Transactional(readOnly = false)
	void save(DataModule entity);

}
