package com.dao;

import org.springframework.transaction.annotation.Transactional;

import com.model.CashModule;
import com.model.Modul;
import com.model.TempCollection;

public interface CashModuleDAO {

	@Transactional(readOnly = false)
	void saveCashModule(CashModule entity);

	@Transactional(readOnly = true)
	Double getSummCollection(Modul modul);
	
	@Transactional(readOnly = true)
	Integer getCountBond(Modul modul);
	
	@Transactional(readOnly = false)
	public void deleteAllTempCollection(Modul modul);
	
	@Transactional(readOnly = false)
	void setCollection(Modul modul, Double plan, Double fakt);

	@Transactional(readOnly = false)
	void setCollection(Modul modul);
	
	@Transactional(readOnly = true)
	TempCollection getLastTempCollection(Modul modul);

}
