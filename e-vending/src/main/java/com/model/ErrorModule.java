package com.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "errorModule")
public class ErrorModule {

	@Id
	@Column(name = "error_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;

	@Column(name = "error", length = 100)
	private String		error;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;

	public ErrorModule() {
	}

	public ErrorModule(Modul modul, String error, Calendar timeStamp) {
		setModul(modul);
		setError(error);
		setTimeStamp(timeStamp);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

}
