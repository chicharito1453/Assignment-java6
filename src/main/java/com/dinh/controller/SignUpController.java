package com.dinh.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinh.entity.Account;
import com.dinh.help.RegisterService;
import com.dinh.service.AccountService;

@Controller
public class SignUpController {

	@Autowired
	AccountService serviceA;
	@Autowired
	RegisterService serviceRegister;
	@Autowired
	HttpServletRequest request;

	@RequestMapping("/signup")
	public String form(Model m) {
		Account account = new Account();
		m.addAttribute("account", account);
		return "user/register";
	}

	@PostMapping("/signup")
	public String register(@ModelAttribute("account") Account account, Model m)
			throws UnsupportedEncodingException, MessagingException {
		if (!serviceA.isExist(account.getUsername())) {
			serviceRegister.register(account, getSiteURL());
			m.addAttribute("success", "Đăng ký thành công, link kích hoạt tài khoản sẽ được gửi đến email của bạn trong giây lát!");
		} else {
			m.addAttribute("error", "Tài khoản đã tồn tại, vui lòng nhập Username khác!");
		}
		return "user/register";
	}

	@GetMapping("/verify")
	public String verifyUser(@RequestParam("code") String code) {
		if (serviceRegister.verify(code)) {
			return "notify/verify_success";
		} else {
			return "notify/verify_fail";
		}
	}

	private String getSiteURL() {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

}
