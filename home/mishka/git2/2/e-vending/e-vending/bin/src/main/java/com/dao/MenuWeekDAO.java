package com.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.MenuWeek;

public interface MenuWeekDAO {
	
	@Transactional(readOnly = true)
	List<MenuWeek> getAllmenu();
	
	@Transactional(readOnly = false)
	void save(MenuWeek menuWeek);
	
	@Transactional(readOnly = true)
	MenuWeek findWeekById(int id);
	
	@Transactional(readOnly = false)
	void update(MenuWeek week);
}
