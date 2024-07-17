package com.brunobasques.springboot_course.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.brunobasques.springboot_course.contracts.OrderRequest;
import com.brunobasques.springboot_course.entities.Order;
import com.brunobasques.springboot_course.entities.OrderItem;
import com.brunobasques.springboot_course.entities.Payment;
import com.brunobasques.springboot_course.entities.Product;
import com.brunobasques.springboot_course.entities.ProductQuantity;
import com.brunobasques.springboot_course.entities.User;
import com.brunobasques.springboot_course.entities.enums.OrderStatus;
import com.brunobasques.springboot_course.repositories.OrderRepository;
import com.brunobasques.springboot_course.services.exceptions.DatabaseException;
import com.brunobasques.springboot_course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	public List<Order> findAll()
	{
		return orderRepository.findAll();		
	}
	
	public Order findById(Long id)
	{
		Optional<Order> order = orderRepository.findById(id);		
		return order.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Order insert(OrderRequest orderRequest)
	{
		User user = userService.findById(orderRequest.getUserId());
		Order newOrder = new Order(null,
				orderRequest.getMoment(),
				OrderStatus.WAITING_PAYMENT,
				user);
		newOrder = orderRepository.save(newOrder);
		
		for(ProductQuantity productQuantity : orderRequest.getProducts())
		{
			Product product = productService.findById(productQuantity.getProductId());
			OrderItem orderItem = new OrderItem(newOrder, product, productQuantity.getProductQuantity(), product.getPrice());
			orderItemService.insert(orderItem);
		}	
		
		Order newOrderSearchResult = findById(newOrder.getId());
		return newOrderSearchResult;
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
	
	public Order updateStatus(Long id, OrderStatus orderStatus)
	{
		try
		{
			Order entity = orderRepository.getReferenceById(id);
			Order order = findById(id);
			
			order.setOrderStatus(orderStatus);
			
			updateData(entity, order);
			return orderRepository.save(entity);
		}
		catch(EntityNotFoundException e)
		{
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Order updatePayment(Long id)
	{
		try
		{
			Order entity = orderRepository.getReferenceById(id);
			Order order = findById(id);
			Payment payment = new Payment(null, Instant.now(), order);
			
			order.setOrderStatus(OrderStatus.PAID);
			order.setPayment(payment);
			
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
