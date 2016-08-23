package com.dao;

import java.util.Calendar;

import org.springframework.transaction.annotation.Transactional;

import com.model.CashModule;
import com.model.Modul;

public interface CashModuleDAO {

	@Transactional(readOnly = false)
	void saveCashModule(CashModule entity);

	@Transactional(readOnly = false)
	void saveCashModule(Modul modul, Calendar timeStamp, Integer cash, Integer bond, Integer sell, Integer bs);
}
