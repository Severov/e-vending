/**
 * 
 */
package com.web.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

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

	// TODO обязательно настроить логи
	// private static final Logger logger =
	// Logger.getLogger(WelcomReceptionDataFromModuleeController.class);

	@Autowired
	ServletContext			context;

	private Modul			modul;

	@Resource(name = "companyService")
	private CompanyDAO		companyService;

	@Resource(name = "modulService")
	private ModuleDAO		modulService;

	@Resource(name = "tempRegModuleService")
	private TempRegModulDAO	tempRegModuleService;

	@Resource(name = "cashModuleService")
	private CashModuleDAO	cashModuleService;

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
		@RequestParam(value = "collect", defaultValue = "0", required = false) Integer collect,
		@RequestParam(value = "bond", defaultValue = "0", required = false) Integer bond, @RequestParam(value = "sell", required = false) Integer sell,
		@RequestParam(value = "bs", required = false) Integer bs) {

		if (d == null) {
			return null;
		}

		modul = modulService.getModul(d);
		if (modul == null) {
			return null;
		}

		modulService.setCollection(modul);

		// saveDataModule(u, l, temp, temp2);

		// return saveErrorModule( ALARM);
		// saveCashCoin(incoin, outcoin);
		// saveCashNotReception(bond);
		// saveCurentSettingsModule(sett);
		// return "sdfsdf";
		// return "sadfsf";
		// saveDataDoor(k);
		return updateVersionAndTelephon(version, mnum, f01);
		// saveCashModule(cash, bond, sell, bs);

		// return cashModuleService.getSumm(modul).toString();

		// return registrationModule( code);

		// return TempRegModulDAO.generateSecretCode(5);//
		// tempRegModuleDAO.findBySecretCode("777");
		// return "Ok";

		// return d + r;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	private String getTest() {
		return context.getRealPath("/META-INF/");
		// context.getResourcePaths("classpath:META-INF/testScript.sql").toString();
	}

	@RequestMapping(value = "/table", method = RequestMethod.GET)
	private Map<Object, Object> getTable() {

		ArrayList<Object> arr = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			arr.add(new HashMap<String, String>() {
				{
					put("uin", "777");
					put("place", "Moscow");
					put("pc", "666");
					put("Volt", "777");
					put("trademark", "Moscow");
					put("day_summ", "666");
				}
			});
		}

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("rows", arr);
		map.put("total", 5);

		ArrayList<Object> arr1 = new ArrayList<>();
		arr1.add(new HashMap<String, String>() {
			{
				put("uin", "<b>777</b>");
				put("place", "Moscow");
				put("pc", "666");
				put("Volt", "777");
				put("trademark", "Moscow");
				put("day_summ", "666");
			}

		});
		map.put("footer", arr1);

		return map;
	}

	private void setCollect(Integer collect) {
		if (collect == null) {
			return;
		}

		modulService.setCollection(modul);
	}

	private String saveDataModule(Integer u, Integer l, Integer temp, Integer temp2) {
		if (l != 0 || u != 0 || temp != -1000 || temp2 != -1000) {
			modulService.save(new DataModule(modul, Calendar.getInstance(), u, l, temp, temp2));
			String returnVal = "RDM" + modul.getCommandString();

			// очистим накопившиеся команды
			modulService.deleteAll(modul.getCommand());

			return returnVal;
		}

		return "";
	}

	/**
	 * Сохраняет данные положения дверей
	 * 
	 * @param modul
	 * @param k
	 */
	private void saveDataDoor(Integer k) {
		if (k == null) {
			return;
		}

		modulService.save(new DataDoor(k, Calendar.getInstance(), modul));
	}

	private String saveErrorModule(String alarm) {
		if (alarm == null) {
			return "";
		}

		modulService.save(new ErrorModule(modul, alarm, Calendar.getInstance()));
		return "RDM";
	}

	/**
	 * Обрабатывает денежные операции по монетам
	 * 
	 * @param modul
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
	 * @param modul
	 * @param bond
	 */
	private void saveCashNotReception(Integer bond) {
		if (bond > 0) {
			return;
		}

		modulService.save(new CashNotReception(modul, Calendar.getInstance()));
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
	private String updateVersionAndTelephon(String version, String mnum, String f01) {
		if (version == null || mnum == null || f01 == null) {
			return "";
		}

		modul.setActivenum(f01);
		modul.setTelephon(mnum);
		modul.setVersion(version);

		modulService.saveOrUpdate(modul);

		String returnVal = "RDM" + modul.getCommandString();

		// Удалим отправленные команды
		modulService.deleteAll(modul.getCommand());

		return returnVal;
	}

	/**
	 * Установка параметров настройки, которые модуль прислал
	 * 
	 * @param modul
	 *            - модуль которому предназначаются настройки
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
	 * @param modul
	 *            - регистрируемый модуль за владельцем
	 * @param code
	 *            - секретный код, полученный при регистрации через сайт
	 * @return - результат регистрации
	 */
	private String registrationModule(String code) {
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

		// Установим пустые настройки для модуля
		modul.setCurentSettings(new CurentSettingsModule(modul));

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
	private String saveCashModule(Integer cash, Integer bond, Integer sell, Integer bs) {

		if (bond < 0) {
			return "";
		}

		if (cash == 0 && bond == 0 && sell == null) { // к нам ничего не пришло
			return "";
		}

		cashModuleService.saveCashModule(new CashModule(modul, Calendar.getInstance(), cash, bond, sell, bs));

		// TODO для совместимости со старой версией
		// инкассация прошла через веб
		if (cash != 0 && sell != 0 && bs != 0) {
			modulService.setCollection(modul);
		}

		return "RDM";
	}

}
