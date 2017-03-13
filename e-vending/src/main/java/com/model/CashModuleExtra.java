package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "cashCoinExtra")
public class CashModuleExtra {

		@Id
		@Column(name = "cashExtra_id", unique = true, nullable = false)
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long		id;
		
		@Column(name = "cashless")
		private Boolean cashless;
		
		@Column(name = "price")
		private Integer price;
		
		@Column(name = "product")
		private Long product;
		
		@OneToOne(fetch = FetchType.LAZY)
		@PrimaryKeyJoinColumn
		private CashModule cash;
		
		public CashModuleExtra(){}
		
		public CashModuleExtra(String cashless, Integer price, Long product, CashModule cash){
			this.cashless = cashless.equals("1") ? true : false;
			this.price = price;
			this.product = product;
			this.cash = cash;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Boolean getCashless() {
			return cashless;
		}

		public void setCashless(Boolean cashless) {
			this.cashless = cashless;
		}

		public Integer getPrice() {
			return price;
		}

		public void setPrice(Integer price) {
			this.price = price;
		}

		public Long getProduct() {
			return product;
		}

		public void setProduct(Long product) {
			this.product = product;
		}

		public CashModule getCash() {
			return cash;
		}

		public void setCash(CashModule cash) {
			this.cash = cash;
		}		
}
