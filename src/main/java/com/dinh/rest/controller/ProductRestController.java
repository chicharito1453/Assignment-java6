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

import com.dinh.entity.Product;
import com.dinh.service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductRestController {

	@Autowired
	ProductService productService;
	
	@GetMapping("/rest/products")
	public List<Product>searchP(){
		return productService.findAll();
	}
	
	@GetMapping("/rest/products/{id}")
	public Product getOne(@PathVariable("id")Integer id) {
		return productService.getOne(id);
	}
	
	@PostMapping("/rest/products")
	public Product create(@RequestBody Product p) {
		return productService.save(p);
	}
	
	@PutMapping("/rest/products")
	public Product update(@RequestBody Product p) {
		return productService.save(p);
	}
	
	@DeleteMapping("/rest/products/{id}")
	public Boolean delete(@PathVariable("id")Integer idProduct) {
		if(productService.getOne(idProduct).getOrderDetails().size()<1) {
			productService.delete(idProduct);
			return true;
		}
		return false;
	}
	
}
