package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "curentSettingsModule")
public class CurentSettingsModule {

	@Id
	@Column(name = "settings_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;

	@Column(name = "time", length = 10)
	private String	time;

	@Column(name = "profile", length = 10)
	private String	profile;

	@Column(name = "balance", length = 10)
	private String	balance;

	@Column(name = "request", length = 10)
	private String	request;

	@Column(name = "silent", length = 10)
	private String	silent;

	@Column(name = "voice", length = 10)
	private String	voice;

	@Column(name = "igprs", length = 10)
	private String	igprs;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	private Modul	modul;

	public CurentSettingsModule() {
	}

	public CurentSettingsModule(Modul modul) {
		this.modul = modul;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getSilent() {
		return silent;
	}

	public void setSilent(String silent) {
		this.silent = silent;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getIgprs() {
		return igprs;
	}

	public void setIgprs(String igprs) {
		this.igprs = igprs;
	}

}
