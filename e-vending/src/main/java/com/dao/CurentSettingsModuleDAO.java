package com.dao;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.model.CurentSettingsModule;
import com.model.Modul;

public interface CurentSettingsModuleDAO {

	@Transactional(readOnly = false)
	void delete(Modul entity);

	@Transactional(readOnly = false)
	void saveAll(Set<CurentSettingsModule> settings);
}
