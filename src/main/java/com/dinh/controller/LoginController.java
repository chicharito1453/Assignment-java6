package com.dinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.help.SessionService;
import com.dinh.service.AccountService;

@Controller
public class LoginController {
	
	@Autowired
	SessionService session;
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/login")
	public String login(Model m) {
		String error = session.get("error");
		String success = session.get("success");
		if(error!=null) {
			m.addAttribute("error",error);
		}
		if(success!=null) {
			m.addAttribute("success",success);
		}
		session.remove("error");
		session.remove("success");
		return "user/login";
	}
	
	@RequestMapping("/login/success")
	public String loginsuccess() {
		session.set("success","Đăng nhập thành công!");
		return "redirect:/login";
	}
	
	@RequestMapping("/login/error")
	public String loginerror() {
		return "redirect:/login";
	}
	
	@RequestMapping("/access/denied")
	public String access() {
		session.set("error","Không có quyền truy xuất!");
		return "redirect:/login";
	}
	
}
