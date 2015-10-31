package com.dao;

import com.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao {

	User findByUserName(String username);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void saveUser(User user);

}