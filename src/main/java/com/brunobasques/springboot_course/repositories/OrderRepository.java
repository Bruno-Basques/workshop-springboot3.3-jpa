package com.brunobasques.springboot_course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunobasques.springboot_course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
