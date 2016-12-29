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

/**
 * Содержит подтвержденные инкассации модуля
 * 
 * @author mishka
 *
 */
@Entity
@Table(name = "collectionModule")
public class CollectionModule {

	@Id
	@Column(name = "collection_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;

	@Column(name = "plan")
	private double		plan;

	@Column(name = "fakt")
	private double		fakt;
	
	//Количество купюр инкассации
	@Column(name = "bs")
	private double		bs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;

	public CollectionModule() {
	}

	public CollectionModule(Modul modul, Calendar timeStamp, double plan, double fakt) {
		this.modul = modul;
		this.plan = plan;
		this.fakt = fakt;
		this.timeStamp = timeStamp;
	}

	public CollectionModule(Modul modul, double plan, double fakt) {
		this.modul = modul;
		this.plan = plan;
		this.fakt = fakt;
		this.timeStamp = Calendar.getInstance();
	}

	public CollectionModule(Modul modul, Calendar timeStamp) {
		this.modul = modul;
		this.timeStamp = timeStamp;
	}

	public CollectionModule(Modul modul, double fakt) {
		this.modul = modul;
		this.plan = fakt;
		this.fakt = fakt;
		this.timeStamp = Calendar.getInstance();
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

	public double getPlan() {
		return plan;
	}

	public void setPlan(double plan) {
		this.plan = plan;
	}

	public double getFakt() {
		return fakt;
	}

	public void setFakt(double fakt) {
		this.fakt = fakt;
	}

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

	public double getBs() {
		return bs;
	}

	public void setBs(double bs) {
		this.bs = bs;
	}

}
