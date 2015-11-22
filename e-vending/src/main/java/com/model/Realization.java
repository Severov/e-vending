package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "realization")
public class Realization {
	
	@Id
    @Column(name = "modul_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@Column(name = "user")
	@ManyToOne()
	@JoinColumn(name = "id")
	private User user;
	
	@Column(name = "food")
	private Food food;
	
	@Column(name = "price")
	private Integer price;
	

}
