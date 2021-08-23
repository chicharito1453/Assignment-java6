package com.dinh.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.OrderDetail;
import com.dinh.service.OrderDetailService;

@CrossOrigin("*")
@RestController
public class OrderDetailRestController {

	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/rest/orderDetails")
	public List<OrderDetail>getAll(){
		return orderDetailService.findAll();
	}
	
}
