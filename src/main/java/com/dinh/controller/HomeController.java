package com.dinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.entity.Product;
import com.dinh.service.AccountService;
import com.dinh.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;

	@RequestMapping("/home")
	public String home(Model m) {
		// 5 SP mới
		Sort sort = Sort.by(Direction.DESC, "createDate");
		Pageable pageable = PageRequest.of(0, 5, sort);
		Page<Product> newItems = productService.findAll(pageable);
		// SP khuyến mãi
		Sort sort2 = Sort.by(Direction.DESC, "discount");
		List<Product> items = productService.findAll(sort2);
		m.addAttribute("newItems", newItems);
		m.addAttribute("items", items);
		return "user/home";
	}

	@RequestMapping("/help")
	public String faq() {
		return "user/faq";
	}

	@RequestMapping("/admin/home")
	public String admin(Model m) {
		return "redirect:/assets/admin/layout/indexAdmin.html";
	}

}
