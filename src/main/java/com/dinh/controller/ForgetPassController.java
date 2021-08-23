package com.dinh.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.entity.Account;
import com.dinh.help.RegisterService;
import com.dinh.service.AccountService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgetPassController {

	@Autowired
	AccountService accountService;
	@Autowired
	RegisterService registerService;

	@RequestMapping("/forgetP")
	public String form(Model m) {
		Account account = new Account();
		m.addAttribute("account", account);
		return "user/forgetP";
	}

	@PostMapping("/forgetP")
	public String getNewPass(@ModelAttribute("account") Account account, Model m)
			throws UnsupportedEncodingException, MessagingException {
		if (accountService.isExist(account.getUsername())) {
			Account a = accountService.getOne(account.getUsername());
			if (account.getEmail().equalsIgnoreCase(a.getEmail())) {
				String password = RandomString.make(6).toLowerCase();
				a.setPassword(password);
				registerService.sendMail(a);
				accountService.save(a);
				m.addAttribute("success", "Mật khẩu mới đã được gửi vào mail của bạn!");
			} else {
				m.addAttribute("error", "Email không khớp với tài khoản!");
			}
		} else {
			m.addAttribute("error", "Tài khoản không tồn tại!");
		}
		return "user/forgetP";
	}

}
