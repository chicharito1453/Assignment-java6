package com.dinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinh.entity.Product;
import com.dinh.service.AccountService;
import com.dinh.service.ProductService;

@Controller
public class SearchController {

	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;

	@PostMapping("/searchProduct")
	public String search(@RequestParam("name") String name, Model m) {
		if (productService.findByNameContaining(name) != null) {
			List<Product> items = productService.findByNameContaining(name);
			if (items.size() == 1) {
				return "redirect:/productSingle/" + items.get(0).getId();
			} else {
				m.addAttribute("items", items);
			}
		}
		return "user/searchResult";
	}

}
