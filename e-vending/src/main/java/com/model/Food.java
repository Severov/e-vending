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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "food")
public class Food {
	
    @Id
    @Column(name = "food_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = 70)
    private String name;
    
    @Column(name = "description", nullable = false, length = 150)
    private String description;
    
    @Column(name = "price", nullable = false)
    private Integer price;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
    private List<Realization> realization; 
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "food")
    private List<MenuWeek> menu_week;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Realization> getRealization() {
		return realization;
	}

	public void setRealization(List<Realization> realization) {
		this.realization = realization;
	}

	public List<MenuWeek> getMenu_week() {
		return menu_week;
	}

	public void setMenu_week(List<MenuWeek> menu_week) {
		this.menu_week = menu_week;
	}

}
