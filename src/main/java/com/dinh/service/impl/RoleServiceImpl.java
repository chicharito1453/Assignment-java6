package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinh.dao.RoleDao;
import com.dinh.entity.Role;
import com.dinh.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
	
}
