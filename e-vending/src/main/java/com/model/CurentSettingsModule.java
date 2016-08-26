package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "curentSettingsModule")
public class CurentSettingsModule {

	@Id
	@Column(name = "settings_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;

	@Column(name = "settings", nullable = false, length = 10)
	private String	settings;

	@Column(name = "value", nullable = false, length = 10)
	private String	value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	private Modul	modul;

	public CurentSettingsModule() {
	}

	public CurentSettingsModule(String settings, String value) {
		setSettings(settings);
		setValue(value);
	}

	public CurentSettingsModule(Modul modul, String settings, String value) {
		setSettings(settings);
		setValue(value);
		setModul(modul);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

}
