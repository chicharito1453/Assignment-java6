package com.dinh.service;

import java.util.List;

import com.dinh.entity.Account;

public interface AccountService {

	Account getOne(String username);

	Account save(Account account);
	
	Account save(Account account, boolean isEncode);

	Account findByVerifyCode(String verifycode);

	boolean isExist(String username);
	
	boolean isAdmin(String username);

	String getUserName();

	boolean chekPass(String rawPass, String encodePass);

	List<Account> getAdministrators();

	List<Account> findAll();
	
}
