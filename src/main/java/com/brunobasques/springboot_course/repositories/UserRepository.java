package com.brunobasques.springboot_course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunobasques.springboot_course.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
