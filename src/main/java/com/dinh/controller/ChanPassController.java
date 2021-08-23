package com.dinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinh.entity.Account;
import com.dinh.service.AccountService;

@Controller
public class ChanPassController {

	@Autowired
	AccountService accountService;

	@RequestMapping("/changeP")
	public String form() {
		return "user/changeP";
	}

	@PostMapping("/changeP")
	public String changeP(@RequestParam("password") String password, Model m,
			@RequestParam("new_password") String new_password,
			@RequestParam("confirm_password") String confirm_password) {
		Account account = accountService.getOne(accountService.getUserName());
		if (accountService.chekPass(password, account.getPassword())) {
			m.addAttribute("error", "Mật khẩu không đúng!");
		} else if (!new_password.equals(confirm_password)) {
			m.addAttribute("error", "Xác nhận mật khẩu không đúng!");
		} else {
			account.setPassword(confirm_password);
			accountService.save(account);
			m.addAttribute("success", "Đổi mật khẩu thành công!");
		}
		return "user/changeP";
	}

}
