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
import org.hibernate.annotations.Index;

@Entity
@Table(name = "cashModule")
public class CashModule {

	@Id
	@Column(name = "cashModule_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "cash")
	private Integer		cash;

	@Column(name = "bond")
	private Integer		bond;

	@Column(name = "sell")
	private Integer		sell;

	@Column(name = "bs")
	private Integer		bs;

	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Index(name = "timeStampIndex")
	private Calendar	timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;

	public CashModule() {
	}

	public CashModule(Modul modul, Calendar timeStamp, Integer cash, Integer bond, Integer sell, Integer bs) {
		this.modul = modul;
		this.timeStamp = timeStamp;
		this.cash = cash;
		this.bond = bond;
		this.sell = sell;
		this.bs = bs;
	}

	public CashModule(Modul modul, Integer cash, Integer bond, Integer sell, Integer bs) {
		this.modul = modul;
		this.timeStamp = Calendar.getInstance();
		this.cash = cash;
		this.bond = bond;
		this.sell = sell;
		this.bs = bs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCash() {
		return cash;
	}

	public void setCash(Integer cash) {
		this.cash = cash;
	}

	public Integer getBond() {
		return bond;
	}

	public void setBond(Integer bond) {
		this.bond = bond;
	}

	public Integer getSell() {
		return sell;
	}

	public void setSell(Integer sell) {
		this.sell = sell;
	}

	public Integer getBs() {
		return bs;
	}

	public void setBs(Integer bs) {
		this.bs = bs;
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
