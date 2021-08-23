package com.dinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.entity.Account;
import com.dinh.service.AccountService;

@Controller
public class ProfileController {

	@Autowired
	AccountService accountService;

	@RequestMapping("/profile")
	public String form(Model m) {
		Account account = accountService.getOne(accountService.getUserName());
		m.addAttribute("account", account);
		return "user/profile";
	}

	@PostMapping("/profile")
	public String profile(@ModelAttribute("account") Account account, Model m) {
		Account a = accountService.getOne(accountService.getUserName());
		a.setFullname(account.getFullname());
		a.setSdt(account.getSdt());
		a.setEmail(account.getEmail());
		accountService.save(a, false);
		m.addAttribute("success", "Đã lưu thay đổi!");
		return "user/profile";
	}
}
