package com.dinh.service;

import java.util.List;

import com.dinh.entity.Account;
import com.dinh.entity.Authority;
import com.dinh.entity.Role;

public interface AuthorityService {

	List<Role>findRolesByUsername(String username);
	
	List<Authority>findAll();

	Authority save(Account account);

	List<Authority> findAuthoritiesOfAdministrators();

	Authority create(Authority auth);

	void delete(Integer id);
	
}
