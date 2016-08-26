package com.dao;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.model.CurentSettingsModule;

public interface CurentSettingsModuleDAO {

	@Transactional(readOnly = false)
	void deleteAll(Set<CurentSettingsModule> settings);

	@Transactional(readOnly = false)
	void saveAll(Set<CurentSettingsModule> settings);
}
