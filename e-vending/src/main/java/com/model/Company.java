package com.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@Column(name = "compani_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;

	@Column(name = "name")
	private Integer		name;

	@Column(name = "description")
	private String		description;

	@Column(name = "fax")
	private String		fax;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
	private List<Modul>	modul;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public List<Modul> getModul() {
		return modul;
	}

	public void setModul(List<Modul> modul) {
		this.modul = modul;
	}

}
