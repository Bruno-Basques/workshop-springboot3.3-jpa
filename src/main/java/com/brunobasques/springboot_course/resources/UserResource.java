package com.brunobasques.springboot_course.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunobasques.springboot_course.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@GetMapping
	public ResponseEntity<User> findAll()
	{
		User u = new User(1L, "User 1", "Email User 1", "Phone User 1", "Password User 1");
		return ResponseEntity.ok().body(u);
	}
}
