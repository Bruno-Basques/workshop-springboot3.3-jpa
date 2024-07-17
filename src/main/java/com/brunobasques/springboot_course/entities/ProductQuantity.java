package com.brunobasques.springboot_course.entities;

import java.io.Serializable;

public class ProductQuantity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long productId;
	
	private Integer productQuantity;
	
	public ProductQuantity(Long productId, Integer productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

	public Long getProductId() {
		return productId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}	
}
