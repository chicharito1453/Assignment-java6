package com.dinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.service.AccountService;

@Controller
public class CartController {

	@Autowired
	AccountService accountService;

	@RequestMapping("/cart")
	public String form() {
		return "user/cart";
	}

}
