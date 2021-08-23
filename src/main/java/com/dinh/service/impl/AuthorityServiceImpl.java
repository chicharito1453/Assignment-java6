package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinh.dao.AccountDao;
import com.dinh.dao.AuthorityDao;
import com.dinh.entity.Account;
import com.dinh.entity.Authority;
import com.dinh.entity.Role;
import com.dinh.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	AuthorityDao authDao;
	@Autowired
	AccountDao daoA;

	@Override
	public List<Role> findRolesByUsername(String username) {
		return authDao.findRolesByUsername(username);
	}

	@Override
	public List<Authority> findAll() {
		return authDao.findAll();
	}

	@Override
	public Authority save(Account account) {
		Authority auth = new Authority();
		Role role = new Role();
		auth.setAccount(account);
		role.setId("USER");
		auth.setRole(role);
		return authDao.save(auth);
	}

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account>accounts = daoA.getAdministrators();
		return authDao.findAuthoritiesOfAdministrators(accounts);
	}

	@Override
	public Authority create(Authority auth) {
		return authDao.save(auth);
	}

	@Override
	public void delete(Integer id) {
		authDao.deleteById(id);
	}
	
}
