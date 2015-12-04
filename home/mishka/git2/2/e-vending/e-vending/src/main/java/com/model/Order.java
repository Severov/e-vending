package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_user")
public class Order {

	@Id
    @Column(name = "order_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_food_id")
	private OrderFood orderFood;
	
	@Column(name = "count")
	private double count;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "food_id")
	private Food food;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
}
