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
@Table(name = "resetKup")
public class ResetKup {
	
	@Id
	@Column(name = "resetKup_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long					id;
	
	@Column(name = "timeStamp", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar	timeStamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modul_id")
	@JsonIgnore
	private Modul		modul;
	
	public ResetKup(){}
	
	public ResetKup(Modul modul, Calendar timestamp){
		this.modul = modul;
		this.timeStamp = timestamp;
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

	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

}
