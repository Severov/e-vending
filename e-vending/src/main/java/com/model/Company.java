package com.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
	@Column(name = "company_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long				id;

	@Column(name = "name")
	private String				name;

	@Column(name = "description")
	private String				description;

	@Column(name = "fax")
	private String				fax;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
	private List<Modul>			modul;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private Set<TempRegModule>	tempRegModul;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "company")
	private Set<User>			user;
	
	public Company(){}
	
	public Company(String name){
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public Set<TempRegModule> getTempRegModul() {
		return tempRegModul;
	}

	public void setTempRegModul(Set<TempRegModule> tempRegModul) {
		this.tempRegModul = tempRegModul;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
}
