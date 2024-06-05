package com.brunobasques.springboot_course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brunobasques.springboot_course.entities.Order;
import com.brunobasques.springboot_course.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll()
	{
		List<Order> listOrders = orderService.findAll();
		return ResponseEntity.ok().body(listOrders);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id)
	{		
		Order order = orderService.findById(id);
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping
	public ResponseEntity<Order> insert(@RequestBody Order order)
	{
		order = orderService.insert(order);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(order.getId())
				.toUri();
		return ResponseEntity.created(uri).body(order);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order)
	{
		order = orderService.update(id, order);
		return ResponseEntity.ok().body(order);
	}
}
