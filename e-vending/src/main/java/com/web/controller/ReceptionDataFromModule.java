/**
 * 
 */
package com.web.controller;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CompanyDAO;
import com.dao.DataDoorDAO;
import com.dao.ModuleDAO;
import com.model.DataDoor;
import com.model.Modul;

/**
 * @author mishka
 *
 *         Клас предназначен для приёма данных от модуля по методу GET
 * 
 */
@RestController
@RequestMapping(value = "/ws")
public class ReceptionDataFromModule {

	@Resource(name = "companyDAO")
	private CompanyDAO	company;

	@Resource(name = "modulDAO")
	private ModuleDAO	modulDAO;

	@Resource(name = "dataDoorDAO")
	private DataDoorDAO	dataDoorDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/company")
	public String receptionData(@RequestParam(value = "d") String d, @RequestParam(value = "r", required = false) String r,
			@RequestParam(value = "version", required = false) String version, @RequestParam(value = "MNUM", required = false) String mnum,
			@RequestParam(value = "F01", required = false) String f01, @RequestParam(value = "k", required = false) Integer k) {

		Modul modul = modulDAO.getModul(d);
		saveDataDoor(modul, k);

		updateVersionAndTelephon(modul, version, mnum, f01);

		return "Ok";
		// return d + r;
	}

	/**
	 * Сохраняет данные положения дверей
	 * 
	 * @param modul
	 * @param k
	 */
	private void saveDataDoor(Modul modul, Integer k) {
		if (k == null) {
			return;
		}

		dataDoorDAO.save(new DataDoor(k, Calendar.getInstance(), modul));
	}

	/**
	 * Обновляет данные про версию прошивки и номера телефонов
	 * 
	 * @param modul
	 *            - модуль прислвший данные
	 * @param version
	 *            - версия прошивки
	 * @param mnum
	 *            - активный номер
	 * @param f01
	 *            - номер владельца модуля
	 */
	private void updateVersionAndTelephon(Modul modul, String version, String mnum, String f01) {
		if (version == null || mnum == null || f01 == null) {
			return;
		}

		modul.setActivenum(f01);
		modul.setTelephon(mnum);
		modul.setVersion(version);

		modulDAO.saveOrUpdate(modul);
	}

}
