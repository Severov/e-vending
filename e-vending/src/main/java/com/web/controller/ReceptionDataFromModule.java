/**
 * 
 */
package com.web.controller;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CashModuleDAO;
import com.dao.CompanyDAO;
import com.dao.ModuleDAO;
import com.dao.TempRegModulDAO;
import com.model.CashCoin;
import com.model.CashModule;
import com.model.CashNotReception;
import com.model.CurentSettingsModule;
import com.model.DataDoor;
import com.model.DataModule;
import com.model.ErrorModule;
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

	private static final Logger logger = Logger.getLogger(ReceptionDataFromModule.class);

	@Autowired
	ServletContext			context;

	@Resource(name = "companyService")
	private CompanyDAO		companyService;

	@Resource(name = "modulService")
	private ModuleDAO		modulService;

	@Resource(name = "tempRegModuleService")
	private TempRegModulDAO	tempRegModuleService;

	@Resource(name = "cashModuleService")
	private CashModuleDAO	cashModuleService;
	
	private Modul			modul;

	/**
	 * Обработчик всех входящих данных от модуля Все входящие параметры являются
	 * не обязательными
	 * 
	 * @param d
	 *            - идентификатор модуля
	 * @param r
	 * @param version
	 *            - версия прошивки
	 * @param mnum
	 * @param f01
	 * @param k
	 * @param lat
	 * @param lng
	 *            - состояние положения дверей
	 * @param sett
	 *            - строка настроек модуля
	 * @param code
	 *            - секретный код при регистрации модуля
	 * @param cash
	 *            - сумма в купюрнике
	 * @param bond
	 *            - номинал поступившей купюры
	 * @param sell
	 *            - событие продажи
	 * @param bs
	 *            - количество купюр в купюроприёмнике
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/company")
	public String receptionData(@RequestParam(value = "d", required = false) String d, @RequestParam(value = "r", required = false) String r,
		@RequestParam(value = "version", required = false) String version, @RequestParam(value = "MNUM", required = false) String mnum,
		@RequestParam(value = "F01", required = false) String f01, @RequestParam(value = "k", required = false) Integer k,
		@RequestParam(value = "sett", required = false) String sett, @RequestParam(value = "code", required = false) String code,
		@RequestParam(value = "incoin", required = false) String incoin, @RequestParam(value = "outcoin", required = false) String outcoin,
		@RequestParam(value = "l", defaultValue = "0", required = false) Integer l, @RequestParam(value = "u", defaultValue = "0", required = false) Integer u,
		@RequestParam(value = "t", defaultValue = "-1000", required = false) Integer temp,
		@RequestParam(value = "t2", defaultValue = "-1000", required = false) Integer temp2, @RequestParam(value = "ALARM", required = false) String ALARM,
		@RequestParam(value = "cash", defaultValue = "0", required = false) Integer cash,
		@RequestParam(value = "collect", required = false) Integer collect,
		@RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lng", required = false) String lng,
		@RequestParam(value = "bond", defaultValue = "0", required = false) Integer bond, @RequestParam(value = "sell", required = false) Integer sell,
		@RequestParam(value = "bs", required = false) Integer bs) {
			
		if (d == null) {
			return null;
		}
		
		logger.info(d.toString() + "    t   -> " + temp.toString());
		logger.info(d.toString() + "    lat -> " + lat);
		logger.info(d.toString() + "    lng -> " + lng);

		modul = modulService.getModul(d);
		if (modul == null) {
			return null;
		}

		saveCashCoin(incoin, outcoin);
		saveCashNotReception(bond);
		saveCurentSettingsModule(sett);
		saveDataDoor(k);
		setCollect(collect);
		setLatLng(lat, lng);

		String returnVal = "";
		if (saveCashModule(cash, bond, sell, bs) ||
			registrationModule(code) ||	
		    updateVersionAndTelephon(version, mnum, f01) ||
		    saveErrorModule(ALARM) ||
		    saveDataModule(u, l, temp, temp2)){
	
			returnVal = "RDM" + modul.getCommandString();

			// Удалим отправленные команды
			modulService.deleteAll(modul.getCommand());
		}
		
		return returnVal;
	}
	
	private void setLatLng(String lat, String lng){
		if (lat == null || lng == null){
			return;
		}
		
		modul.setLat(lat);
		modul.setLng(lng);
		
		modulService.saveOrUpdate(modul);
	}

	private void setCollect(Integer collect) {
		if (collect == null) {
			return;
		}

		modulService.setCollection(modul);
	}

	private boolean saveDataModule(Integer u, Integer l, Integer temp, Integer temp2) {
		if (l != 0 || u != 0 || temp != -1000 || temp2 != -1000) {
			modulService.save(new DataModule(modul, Calendar.getInstance(), u, l, temp, temp2));
			return true;
		}

		return false;
	}

	/**
	 * Сохраняет данные положения дверей
	 * 
	 * @param k
	 */
	private void saveDataDoor(Integer k) {
		if (k == null) {
			return;
		}

		modulService.save(new DataDoor(k, Calendar.getInstance(), modul));
	}

	private boolean saveErrorModule(String alarm) {
		if (alarm == null) {
			return false;
		}

		modulService.save(new ErrorModule(modul, alarm, Calendar.getInstance()));
		return true;
	}

	/**
	 * Обрабатывает денежные операции по монетам
	 * 
	 * @param in
	 * @param out
	 */
	private void saveCashCoin(String in, String out) {
		String type = "0";
		String[] buf;

		if (in != null) { // к нам пришли монеты
			buf = in.split(";");
		} else if (out != null) { // от нас ушли монеты
			buf = out.split(";");
			type = "1";
		} else { // к нам ничего не пришло
			return;
		}

		modulService.save(new CashCoin(modul, type, buf[0], buf[1], Calendar.getInstance()));
	}

	/**
	 * Фиксация отказа купюрника от купюры
	 * 
	 * @param bond
	 */
	private void saveCashNotReception(Integer bond) {
		if (bond >= 0) {
			return;
		}

		modulService.save(new CashNotReception(modul, Calendar.getInstance()));
	}

	/**
	 * Обновляет данные про версию прошивки и номера телефонов
	 * 
	 * @param version
	 *            - версия прошивки
	 * @param mnum
	 *            - активный номер
	 * @param f01
	 *            - номер владельца модуля
	 */
	private boolean updateVersionAndTelephon(String version, String mnum, String f01) {
		if (version == null || mnum == null || f01 == null) {
			return false;
		}

		modul.setActivenum(f01);
		modul.setTelephon(mnum);
		modul.setVersion(version);

		modulService.saveOrUpdate(modul);

		return true;
	}

	/**
	 * Установка параметров настройки, которые модуль прислал
	 * 
	 * @param settings
	 *            - строка настроек
	 */
	private void saveCurentSettingsModule(String settings) {
		if (settings == null) {
			return;
		}

		// Время установим сразу
		if (settings.indexOf("time") != -1) {
			int len = settings.indexOf("time") + "time".length();
			String value = settings.substring(len, len + 4);

			modul.getCurentSettings().setHours(value.substring(0, 2));
			modul.getCurentSettings().setMinutes(value.substring(2, 4));
		}

		Class classSettings = new CurentSettingsModule().getClass();

		SortedSet<String> continuesSet = new TreeSet<String>();
		continuesSet.add("id");
		continuesSet.add("modul");
		continuesSet.add("minutes");
		continuesSet.add("hours");

		for (Field field : classSettings.getDeclaredFields()) {
			String stn = field.getName();

			// служебные поля пропускаем
			if (continuesSet.contains(stn)) {
				continue;
			}

			// значение настройки по умолчанию
			String value = "1";

			// если к нам пришла настройка с другим значением
			if (settings.indexOf(stn) != -1) {
				int len = settings.indexOf(stn) + stn.length();
				value = settings.substring(len, len + 1);
			}

			try {
				field.setAccessible(true);
				field.set(modul.getCurentSettings(), value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		modulService.saveOrUpdate(modul);
	}

	/**
	 * Регистрация модуля за пользователем/организацией
	 * 
	 * @param code
	 *            - секретный код, полученный при регистрации через сайт
	 * @return - результат регистрации
	 */
	private boolean registrationModule(String code) {
		if (code == null) {
			return false;
		}

		TempRegModule tmp = tempRegModuleService.findBySecretCode(code);
		if (tmp == null) {
			// отобразим, что время регистрации истекло 5-ть мин, или
			// предоставленный код не найден
			return false;//"Код не найден, либо время истекло";
		}

		if (modul.isActive()) {
			return false;//"Модуль занят";
		}

		// Установим пустые настройки для модуля
		modul.setCurentSettings(new CurentSettingsModule(modul));
		modul.setCompany(tmp.getCompany());
		modul.setActive(true);
		modulService.saveOrUpdate(modul);
		return true;

	}

	/**
	 * Сохранение данных про финансовую активность модуля
	 * 
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
	private boolean saveCashModule(Integer cash, Integer bond, Integer sell, Integer bs) {

		if (bond < 0) {
			return false;
		}

		if (cash == 0 && bond == 0 && sell == null) { // к нам ничего не пришло
			return false;
		}

		cashModuleService.saveCashModule(new CashModule(modul, Calendar.getInstance(), cash, bond, sell, bs));

		// TODO для совместимости со старой версией
		// инкассация прошла через веб
		if (cash != 0 && sell != 0 && bs != 0) {
			modulService.setCollection(modul);
		}

		return true;
	}

}
