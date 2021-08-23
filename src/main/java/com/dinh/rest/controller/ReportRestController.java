package com.dinh.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.ReportCategory;
import com.dinh.entity.ReportProduct;
import com.dinh.entity.ReportYear;
import com.dinh.service.OrderDetailService;
import com.dinh.service.ProductService;

@CrossOrigin("*")
@RestController
public class ReportRestController {

	@Autowired
	ProductService productService;
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/rest/report/categories")
	public List<ReportCategory>report1(){
		return productService.getInventoryByCategory();
	}
	
	@GetMapping("/rest/report/products")
	public List<ReportProduct>report2(){
		return orderDetailService.getInventoryByProduct();
	}
	
	@GetMapping("/rest/report/years")
	public List<ReportYear>report3(){
		return orderDetailService.getInventoryByYear();
	}
	
}
