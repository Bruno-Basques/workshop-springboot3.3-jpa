package com.brunobasques.springboot_course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunobasques.springboot_course.entities.OrderItem;
import com.brunobasques.springboot_course.entities.PK.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
