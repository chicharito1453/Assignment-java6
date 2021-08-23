package com.dinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinh.entity.Role;

public interface RoleDao extends JpaRepository<Role, String> {
	
}
