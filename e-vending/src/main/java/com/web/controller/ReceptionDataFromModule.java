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

import com.dao.CashModuleDAO;
import com.dao.CompanyDAO;
import com.dao.DataDoorDAO;
import com.dao.ModuleDAO;
import com.dao.TempRegModulDAO;
import com.model.DataDoor;
import com.model.Modul;
import com.model.TempRegModule;

/**
 * @author mishka
 *
 *         Клас предназначен для приёма данных от модуля по методу GET
 * 
 */
@RestController
@RequestMapping(value = "/ws")
public class ReceptionDataFromModule {

	// TODO обязательно настроить логи
	// private static final Logger logger =
	// Logger.getLogger(WelcomReceptionDataFromModuleeController.class);

	@Resource(name = "companyService")
	private CompanyDAO		companyService;

	@Resource(name = "modulService")
	private ModuleDAO		modulService;

	@Resource(name = "dataDoorService")
	private DataDoorDAO		dataDoorService;

	@Resource(name = "tempRegModuleService")
	private TempRegModulDAO	tempRegModuleService;

	@Resource(name = "cashModuleService")
	private CashModuleDAO	cashModuleService;

	@RequestMapping(method = RequestMethod.GET, value = "/company")
	public String receptionData(@RequestParam(value = "d", required = false) String d, @RequestParam(value = "r", required = false) String r,
			@RequestParam(value = "version", required = false) String version, @RequestParam(value = "MNUM", required = false) String mnum,
			@RequestParam(value = "F01", required = false) String f01, @RequestParam(value = "k", required = false) Integer k,
			@RequestParam(value = "code", required = false) String code, @RequestParam(value = "cash", required = false) Integer cash,
			@RequestParam(value = "bond", required = false) Integer bond, @RequestParam(value = "sell", required = false) Integer sell,
			@RequestParam(value = "bs", required = false) Integer bs) {

		if (d == null) {
			return null;
		}

		Modul modul = modulService.getModul(d);
		if (modul == null) {
			return null;
		}

		saveDataDoor(modul, k);
		updateVersionAndTelephon(modul, version, mnum, f01);
		saveCashModule(modul, cash, bond, sell, bs);

		return registrationModule(modul, code);

		// return TempRegModulDAO.generateSecretCode(5);//
		// tempRegModuleDAO.findBySecretCode("777");
		// return "Ok";

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

		dataDoorService.save(new DataDoor(k, Calendar.getInstance(), modul));
	}

	/**
	 * Обновляет данные про версию прошивки и номера телефонов
	 * 
	 * @param modul
	 *            - модуль прислaвший данные
	 * @param version
	 *            - версия прошивки
	 * @param mnum
	 *            - активный номер
	 * @param f01
	 *            - номер владельца модуля
	 */
	private String updateVersionAndTelephon(Modul modul, String version, String mnum, String f01) {
		if (version == null || mnum == null || f01 == null) {
			return "";
		}

		modul.setActivenum(f01);
		modul.setTelephon(mnum);
		modul.setVersion(version);

		modulService.saveOrUpdate(modul);

		return "RDM";
	}

	/**
	 * Регистрация модуля за пользователем/организацией
	 * 
	 * @param modul
	 *            - регистрируемый модуль за владельцем
	 * @param code
	 *            - секретный код, полученный при регистрации через сайт
	 * @return - результат регистрации
	 */
	private String registrationModule(Modul modul, String code) {
		if (code == null) {
			return "";
		}

		TempRegModule tmp = tempRegModuleService.findBySecretCode(code);
		if (tmp == null) {
			// отобразим, что время регистрации истекло 5-ть мин, или
			// предоставленный код не найден

			return "Код не найден, либо время истекло";
		}

		if (modul.isActive()) {
			return "Модуль занят";
		}

		modul.setCompany(tmp.getCompany());
		modul.setActive(true);
		modulService.saveOrUpdate(modul);
		return "RDM";

	}

	/**
	 * Сохранение данных про финансовую активность модуля
	 * 
	 * @param modul
	 *            - модуль, по которому прошло фин. событие
	 * @param cash
	 *            - всего сумма, которая числится в автомате
	 * @param bond
	 *            - номинал купюры
	 * @param sell
	 *            - прошло событие продажи
	 * @param bs
	 *            - количество купюр
	 * @return
	 */
	private String saveCashModule(Modul modul, Integer cash, Integer bond, Integer sell, Integer bs) {

		Integer c = 0;
		Integer b = 0;
		Integer s = null;

		if (cash != null) {
			c = cash;
		}

		if (bond != null) {
			b = bond;

			if (bond < 0) {
				return "";
			}
		}

		if (sell != null) {
			s = sell;
		}

		if (c == 0 && b == 0 && s == null) { // к нам ничего не пришло
			return "";
		}

		cashModuleService.saveCashModule(modul, Calendar.getInstance(), c, b, s, bs);

		return "RDM";
	}

}
