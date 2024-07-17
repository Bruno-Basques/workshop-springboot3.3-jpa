package com.brunobasques.springboot_course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brunobasques.springboot_course.entities.OrderItem;
import com.brunobasques.springboot_course.repositories.OrderItemRepository;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItem insert(OrderItem orderItem)
	{
		return orderItemRepository.save(orderItem);
	}
}
