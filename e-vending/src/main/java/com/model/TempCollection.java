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
@Table(name = "tempCollection")
public class TempCollection {

	@Id
	@Column(name = "temp_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "plan")
	private Double		plan;

	@Column(name = "fakt")
	private Double		fakt;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;

	public TempCollection() {
	}

	public TempCollection(Modul modul, Double plan, Double fakt) {
		setPlan(plan);
		setFakt(fakt);
		this.modul = modul;
		this.timeStamp = Calendar.getInstance();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPlan() {
		return plan;
	}

	public void setPlan(Double plan) {
		this.plan = plan;
	}

	public Double getFakt() {
		return fakt;
	}

	public void setFakt(Double fakt) {
		this.fakt = fakt;
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
