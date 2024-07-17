package com.brunobasques.springboot_course.contracts;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.brunobasques.springboot_course.entities.ProductQuantity;

public class OrderRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Instant moment;	
	
	private List<ProductQuantity> products;
	
	private Long userId;
	
	public OrderRequest() {};

	public OrderRequest(Instant moment, List<ProductQuantity> products, Long userId) {
		super();
		this.moment = moment;
		this.products = products;
		this.userId = userId;
	}

	public Instant getMoment() {
		return moment;
	}

	public List<ProductQuantity> getProducts() {
		return products;
	}

	public Long getUserId() {
		return userId;
	}	
}
