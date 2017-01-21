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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name = "dataModule")
@Table(appliesTo = "dataModule", indexes = {
	      @Index(name="timeStamp_index", columnNames = "timeStamp"),
	      @Index(name="modul_timeStamp_index", columnNames = {"modul_id", "timeStamp"}),
	   })
public class DataModule {

	@Id
	@Column(name = "dataModul_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "u")
	private Integer		u;

	@Column(name = "l")
	private Integer		l;

	@Column(name = "temp")
	private Integer		temp;

	@Column(name = "temp2")
	private Integer		temp2;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;

	public DataModule() {
	}

	public DataModule(Modul modul, Calendar timeStamp, Integer u, Integer l, Integer temp, Integer temp2) {
		setModul(modul);
		setU(u);
		setL(l);
		setTimeStamp(timeStamp);
		setTemp(temp);
		setTemp2(temp2);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getU() {
		return u;
	}

	public void setU(Integer u) {
		this.u = u;
	}

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTemp2() {
		return temp2;
	}

	public void setTemp2(Integer temp2) {
		this.temp2 = temp2;
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
