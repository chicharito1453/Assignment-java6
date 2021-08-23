package com.dinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.service.AccountService;
import com.dinh.service.OrderDetailService;
import com.dinh.service.OrderService;
import com.dinh.service.ProductService;

@Controller
public class CheckoutController {

	@Autowired
	AccountService accountService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	ProductService productService;

	@RequestMapping("/checkout")
	public String form(Model m) {
		m.addAttribute("account", accountService.getOne(accountService.getUserName()));
		return "user/checkout";
	}

}
