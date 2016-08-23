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

@Entity
@Table(name = "dataDoor")
public class DataDoor {
	@Id
	@Column(name = "dataDoor_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer		id;

	@Column(name = "k", nullable = false, length = 50)
	private Integer		k;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	private Modul		modul;

	public DataDoor() {
	};

	public DataDoor(Integer k, Calendar timeStamp, Modul modul) {
		this.k = k;
		this.modul = modul;
		this.timeStamp = timeStamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}
}
