package com.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Класс описывает структуру модуля вендингового автомата
 * 
 * @author mishka
 *
 */
@Entity
@Table(name = "modul")
public class Modul {

	@Id
	@Column(name = "modul_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long					id;

	@Column(name = "imai", nullable = false, length = 50)
	private String					imai;

	@Column(name = "serial_number", nullable = false, length = 50)
	private String					serialNumber;

	@Column(name = "version", nullable = false, length = 50)
	private String					version;

	@Column(name = "telephon", nullable = false, length = 50)
	private String					telephon;

	@Column(name = "activenum", nullable = false, length = 50)
	private String					activenum;

	@Column(name = "uin", nullable = false, length = 50)
	private String					uin;

	@Column(name = "id_device", nullable = false, length = 50)
	private String					idDevice;

	@Column(name = "active", nullable = false, length = 50)
	private boolean					active;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	@JsonIgnore
	private Company					company;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<DataDoor>			dataDoor;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "modul")
	@Cascade({ CascadeType.ALL })
	@JsonIgnore
	private CurentSettingsModule	curentSettings;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<CollectionModule>	collection;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private Set<CashCoin>			cashCoin;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "modul")
	@JsonIgnore
	private Set<CommandToModule>	command;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<CashModule>		cashModule;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<ErrorModule>		error;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private Set<DataModule>			dataModul;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<CashNotReception>	cashNotReception;

	public Long getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImai() {
		return imai;
	}

	public void setImai(String imai) {
		this.imai = imai;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}

	public List<DataDoor> getDataDoor() {
		return dataDoor;
	}

	public void setDataDoor(List<DataDoor> dataDoor) {
		this.dataDoor = dataDoor;
	}

	public String getTelephon() {
		return telephon;
	}

	public void setTelephon(String telephon) {
		this.telephon = telephon;
	}

	public String getActivenum() {
		return activenum;
	}

	public void setActivenum(String activenum) {
		this.activenum = activenum;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<CollectionModule> getCollection() {
		return collection;
	}

	public void setCollection(List<CollectionModule> collection) {
		this.collection = collection;
	}

	public List<CashModule> getCashModule() {
		return cashModule;
	}

	public void setCashModule(List<CashModule> cashModule) {
		this.cashModule = cashModule;
	}

	public CurentSettingsModule getCurentSettings() {
		return curentSettings;
	}

	public void setCurentSettings(CurentSettingsModule curentSettings) {
		this.curentSettings = curentSettings;
	}

	public List<CashNotReception> getCashNotReception() {
		return cashNotReception;
	}

	public void setCashNotReception(List<CashNotReception> cashNotReception) {
		this.cashNotReception = cashNotReception;
	}

	public Set<CashCoin> getCashCoin() {
		return cashCoin;
	}

	public void setCashCoin(Set<CashCoin> cashCoin) {
		this.cashCoin = cashCoin;
	}

	public Set<CommandToModule> getCommand() {
		return command;
	}

	/**
	 * Возвращает собранную строку с командами вида [command[param1][param2]]
	 * 
	 * @return
	 */
	public String getCommandString() {
		if (getCommand() == null || getCommand().size() == 0) {
			return "";
		}

		String buf = "";
		for (CommandToModule command : getCommand()) {
			buf += " " + command.getCommand() + command.getParam1() + command.getParam2();
		}

		return buf;
	}

	public void setCommand(Set<CommandToModule> command) {
		this.command = command;
	}

	public List<ErrorModule> getError() {
		return error;
	}

	public void setError(List<ErrorModule> error) {
		this.error = error;
	}
}
