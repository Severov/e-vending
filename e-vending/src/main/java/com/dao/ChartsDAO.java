package com.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Modul;

public interface ChartsDAO {
	
	@Transactional(readOnly = true)
	List<?> getChartBond(Modul modul);

	@Transactional(readOnly = true)
	List<?> getChartCollection(Modul modul);
}
