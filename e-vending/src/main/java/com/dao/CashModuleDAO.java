package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.CashModule;
import com.model.Modul;

public interface CashModuleDAO {

	@Transactional(readOnly = false)
	void saveCashModule(CashModule entity);

	@Transactional(readOnly = true)
	Integer getSumm(Modul modul);

}
