package com.dinh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinh.dao.AccountDao;
import com.dinh.dao.AuthorityDao;
import com.dinh.entity.Account;
import com.dinh.entity.Role;
import com.dinh.help.SessionService;
import com.dinh.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
	@Autowired
	AccountDao daoA;
	@Autowired
	AuthorityDao authDao;
	@Autowired
	SessionService session;
	@Autowired
	BCryptPasswordEncoder pe;

	@Override
	public Account getOne(String username) {
		return daoA.findById(username).get();
	}

	@Override
	public Account findByVerifyCode(String verifycode) {
		return daoA.findByVerifyCode(verifycode);
	}

	@Override
	public boolean isExist(String username) {
		return daoA.existsById(username);
	}

	@Override
	public Account save(Account account) {
		account.setPassword(pe.encode(account.getPassword().trim()));;
		account.setUsername(account.getUsername().trim());
		return daoA.save(account);
	}

	@Override
	public Account save(Account account, boolean isEncode) {
		account.setPassword(account.getPassword());
		return daoA.save(account);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Account account = daoA.findById(username).get();
			String password = account.getPassword();
			boolean enabled = false;
			if(account.isActivated()) {
				enabled = false;
			} else {
				session.set("error","Tài khoản chưa được kích hoạt!");
				enabled = true;
			}
			String[]roles = authDao.findRolesByUsername(username).stream()
									.map(role -> role.getId())
									.collect(Collectors.toList()).toArray(new String[0]);		
			return User.withUsername(username)
					   .password(password)
					   .roles(roles)
					   .disabled(enabled).build();
		} catch (Exception e) {
			session.set("error","Đăng nhập không đúng!");
			throw new UsernameNotFoundException(username + "not found!");
		}
	}

	@Override
	public String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername(); 
		}
		return null;
	}

	@Override
	public boolean chekPass(String rawPass, String encodePass) {
		if(pe.matches(rawPass, encodePass)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isAdmin(String username) {
		List<Role>roles = authDao.findRolesByUsername(username);
		boolean isAD = false;
		for (Role role : roles) {
			if(role.getId().equalsIgnoreCase("STAFF") || role.getId().equalsIgnoreCase("ADMIN")) {
				isAD = true;
				break;
			}
		}
		return isAD;
	}

	@Override
	public List<Account> getAdministrators() {
		return daoA.getAdministrators();
	}

	@Override
	public List<Account> findAll() {
		return daoA.findAll();
	}

}
