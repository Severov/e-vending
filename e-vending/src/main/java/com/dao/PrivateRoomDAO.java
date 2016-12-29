package com.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface PrivateRoomDAO {
	
	@Transactional(readOnly = true)
	List<?> getMainTable();

}
