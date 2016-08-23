package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.DataDoor;

public interface DataDoorDAO {

	@Transactional(readOnly = false)
	void save(DataDoor entity);

}
