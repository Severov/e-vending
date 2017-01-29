package com.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.Company;
import com.model.User;

public interface UserDAO {

	@Transactional(readOnly = true)
	User findByUserName(String username);
	
	@Transactional(readOnly = true)
	User getUserByApiKey(String apiKey);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void save(User user);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void save(Company company);

	User getAuthenticationUser();
}