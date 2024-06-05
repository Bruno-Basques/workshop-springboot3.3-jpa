package com.brunobasques.springboot_course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.brunobasques.springboot_course.entities.Category;
import com.brunobasques.springboot_course.repositories.CategoryRepository;
import com.brunobasques.springboot_course.services.exceptions.DatabaseException;
import com.brunobasques.springboot_course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll()
	{
		return categoryRepository.findAll();		
	}
	
	public Category findById(Long id)
	{
		Optional<Category> category = categoryRepository.findById(id);		
		return category.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Category insert(Category category)
	{
		return categoryRepository.save(category);
	}
	
	public void delete(Long id)
	{
		try 
		{
		categoryRepository.deleteById(id);
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
	
	public Category update(Long id, Category category)
	{
		try
		{
			Category entity = categoryRepository.getReferenceById(id);
			updateData(entity, category);
			return categoryRepository.save(entity);
		}
		catch(EntityNotFoundException e)
		{
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Category entity, Category category) {
		entity.setName(category.getName());	
	}
}
