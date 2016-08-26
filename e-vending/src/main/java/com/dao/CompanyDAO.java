package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.Company;

public interface CompanyDAO {

	@Transactional(readOnly = true)
	Company getCompany(Integer id);
}
