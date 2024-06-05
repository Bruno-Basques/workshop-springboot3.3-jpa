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

import com.brunobasques.springboot_course.entities.Category;
import com.brunobasques.springboot_course.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll()
	{
		List<Category> listCategorys = categoryService.findAll();
		return ResponseEntity.ok().body(listCategorys);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id)
	{		
		Category category = categoryService.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category)
	{
		category = categoryService.insert(category);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(category.getId())
				.toUri();
		return ResponseEntity.created(uri).body(category);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category)
	{
		category = categoryService.update(id, category);
		return ResponseEntity.ok().body(category);
	}
}
