package com.brunobasques.springboot_course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.brunobasques.springboot_course.entities.Order;
import com.brunobasques.springboot_course.repositories.OrderRepository;
import com.brunobasques.springboot_course.services.exceptions.DatabaseException;
import com.brunobasques.springboot_course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> findAll()
	{
		return orderRepository.findAll();		
	}
	
	public Order findById(Long id)
	{
		Optional<Order> order = orderRepository.findById(id);		
		return order.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Order insert(Order order)
	{
		return orderRepository.save(order);
	}
	
	public void delete(Long id)
	{
		try 
		{
		orderRepository.deleteById(id);
		} 
		catch(EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e)
		{
			throw new DatabaseException(e.getMessage());
		}		
	}
	
	public Order update(Long id, Order order)
	{
		try
		{
			Order entity = orderRepository.getReferenceById(id);
			updateData(entity, order);
			return orderRepository.save(entity);
		}
		catch(EntityNotFoundException e)
		{
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Order entity, Order order) {
		entity.setMoment(order.getMoment());
		entity.setOrderStatus(order.getOrderStatus());
		entity.setClient(order.getClient());
		entity.setPayment(order.getPayment());		
	}
}
