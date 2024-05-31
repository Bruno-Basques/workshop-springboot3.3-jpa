package com.brunobasques.springboot_course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunobasques.springboot_course.entities.Order;
import com.brunobasques.springboot_course.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService userService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll()
	{
		List<Order> listOrders = userService.findAll();
		return ResponseEntity.ok().body(listOrders);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id)
	{		
		Order order = userService.findById(id);
		return ResponseEntity.ok().body(order);
	}
}
