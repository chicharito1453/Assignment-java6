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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.Order;
import com.dinh.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("rest/orders")
public class OrderRestController {

	@Autowired
	OrderService orderService;
	
	@GetMapping()
	public List<Order>getAll(){
		return orderService.findAll();
	}
	
	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
	
	@PutMapping()
	public Order update(@RequestBody JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);
		return orderService.save(order);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Long id) {
		orderService.delete(orderService.getOne(id));
	}
	
}
