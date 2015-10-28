package com.mkyong.users.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mkyong.users.model.User;

public interface UserDao {

	User findByUserName(String username);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void saveUser(User user);

}