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
	
	@Column(name = "lat", length = 50)
	private String					lat;
	
	@Column(name = "lng", length = 50)
	private String					lng;

	@Column(name = "uin", unique = true, nullable = false, length = 50)
	private String					uin;

	@Column(name = "id_device", unique = true, nullable = false, length = 50)
	private String					idDevice;

	@Column(name = "trademark", nullable = true, length = 50)
	private String					trademark;

	@Column(name = "place", nullable = true, length = 50)
	private String					place;
	
	@Column(name = "last_balance", nullable = true)
	private String					lastBalance;

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
	private CurrentSettingsModule	currentSettings;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<CollectionModule>	collection;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private List<TempCollection>	tempCollection;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private Set<CashCoin>			cashCoin;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "modul")
	@Cascade({ CascadeType.ALL })
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	@JsonIgnore
	private Set<ResetKup>	resetKup;
	
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

	public CurrentSettingsModule getCurrentSettings() {	
		if(currentSettings == null)
			return new CurrentSettingsModule(this).resetAllSettings();
		
		return currentSettings;
	}

	public void setCurrentSettings(CurrentSettingsModule currentSettings) {
		this.currentSettings = currentSettings;
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
	
	public String getCommandString(){
		if (command == null || command.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (CommandToModule entity : command) {
			builder.append(" ");
			builder.append(entity.getCommand());
		}

		return builder.toString();
	}

	public Set<CommandToModule> getCommand() {
		return command;
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

	public List<TempCollection> getTempCollection() {
		return tempCollection;
	}

	public void setTempCollection(List<TempCollection> tempCollection) {
		this.tempCollection = tempCollection;
	}

	public Set<DataModule> getDataModul() {
		return dataModul;
	}

	public void setDataModul(Set<DataModule> dataModul) {
		this.dataModul = dataModul;
	}

	public String getTrademark() {
		return trademark;
	}

	public String getPlace() {
		return place;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Set<ResetKup> getResetKup() {
		return resetKup;
	}

	public void setResetKup(Set<ResetKup> resetKup) {
		this.resetKup = resetKup;
	}

	public String getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}
}
