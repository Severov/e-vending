package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.TempRegModule;

public interface TempRegModulDAO {

	@Transactional(readOnly = true)
	String generateSecretCode(int length);

	@Transactional(readOnly = true)
	TempRegModule findBySecretCode(String secret);

}
