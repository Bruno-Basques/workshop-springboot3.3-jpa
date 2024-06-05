package com.brunobasques.springboot_course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.brunobasques.springboot_course.entities.Category;
import com.brunobasques.springboot_course.entities.Product;
import com.brunobasques.springboot_course.repositories.ProductRepository;
import com.brunobasques.springboot_course.services.exceptions.DatabaseException;
import com.brunobasques.springboot_course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryService categoryService;
	
	public List<Product> findAll()
	{
		return productRepository.findAll();		
	}
	
	public Product findById(Long id)
	{
		Optional<Product> product = productRepository.findById(id);		
		return product.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Product insert(Product product)
	{
		return productRepository.save(product);
	}
	
	public void delete(Long id)
	{
		try 
		{
			productRepository.deleteById(id);
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
	
	public Product update(Long id, Product product)
	{
		try
		{
			Product entity = productRepository.getReferenceById(id);
			updateData(entity, product);
			return productRepository.save(entity);
		}
		catch(EntityNotFoundException e)
		{
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Product entity, Product product) 
	{
		entity.setName(product.getName());
		entity.setDescription(product.getDescription());
		entity.setPrice(product.getPrice());
		entity.setImgUrl(product.getImgUrl());		
	}
	
	public void deleteCategoryFromProduct(Long productId, Long categoryId)
	{
		Product product = findById(productId);			    
		product.removeCategory(categoryId);
		productRepository.save(product);		
	}
	
	public Product addCategoryFromProduct(Long productId, Long categoryId)
	{
		Product product = findById(productId);	
		Category category = categoryService.findById(categoryId);
		product.addCategory(category);
		return productRepository.save(product);		
	}
}
