package com.dao;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.model.CommandToModule;

public interface CommandToModuleDAO {

	@Transactional(readOnly = false)
	void save(CommandToModule entity);

	@Transactional(readOnly = false)
	void deleteAll(Set<CommandToModule> entity);

}
