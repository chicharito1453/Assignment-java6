package com.dinh.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.Role;
import com.dinh.service.AccountService;
import com.dinh.service.AuthorityService;
import com.dinh.service.RoleService;

@CrossOrigin("*")
@RestController
public class RolesRestController {

	@Autowired
	RoleService roleService;
	@Autowired
	AuthorityService authService;
	@Autowired
	AccountService accountService;
	
	@GetMapping("/rest/roles")
	public List<Role>getAll(){
		return roleService.findAll();
	}
	
	@GetMapping("/rest/unauthorized")
	public Boolean isUnauthorized() {
		List<Role>roles = authService.findRolesByUsername(accountService.getUserName());
		boolean isAD = false;
		for (Role role : roles) {
			if(role.getId().equalsIgnoreCase("ADMIN")) {
				isAD = true;
				break;
			}
		}
		return isAD;
	}
	
}
