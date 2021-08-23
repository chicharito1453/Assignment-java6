package com.dinh.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.Account;
import com.dinh.service.AccountService;
import com.dinh.service.AuthorityService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin("*")
@RestController
public class AccountRestController {

	@Autowired
	AccountService accountService;
	 @Autowired
	 AuthorityService authService;

	@GetMapping("/rest/accounts")
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}

	@PutMapping("/rest/accounts")
	public Account update(@RequestBody Account account) {
		Account a = accountService.getOne(account.getUsername());

		// MÃ KÍCH HOẠT
		if (account.isActivated()) {
			account.setVerifycode(null);
		} else {
			account.setVerifycode(RandomString.make(64));
		}

		// MẬT KHẨU
		if (a.getPassword().equals(account.getPassword().trim()) ) { // pass như cũ thì không mã hóa
			return accountService.save(account, false);
		} else { // pass mới thì mã hóa
			return accountService.save(account);
		}
	}

	@PostMapping("/rest/accounts")
	public Account create(@RequestBody Account account) {
		// MÃ KÍCH HOẠT
		if (account.isActivated()) {
			account.setVerifycode(null);
		} else {
			account.setVerifycode(RandomString.make(64));
		}
		accountService.save(account);
		authService.save(account);
		return accountService.getOne(account.getUsername());
	}

}
