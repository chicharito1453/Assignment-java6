package com.dinh.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.Category;
import com.dinh.help.SessionService;
import com.dinh.service.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryRestController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	SessionService serviceS;
	
	@GetMapping("/rest/categories")
	public List<Category>checked(){
		return categoryService.findAll();
	}
	
	@PostMapping("/rest/categories")
	public Category save(@RequestBody Category category) {
		serviceS.remove("categories");
		serviceS.remove("checkP");
		return categoryService.save(category);
	}
	
	@PutMapping("/rest/categories")
	public Category update(@RequestBody Category category) {
		serviceS.remove("categories");
		serviceS.remove("checkP");
		return categoryService.save(category);
	}
	
	@DeleteMapping("/rest/categories/{id}")
	public Boolean delete(@PathVariable("id")String id) {
		if(categoryService.getOne(id.trim()).getProducts().size()<1) {
			categoryService.delete(id);
			return true;
		}
		return false;
	}
	
}
