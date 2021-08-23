package com.dinh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dinh.entity.Account;
import com.dinh.entity.Authority;
import com.dinh.entity.Role;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
	@Query("SELECT o.role FROM Authority o WHERE o.account.username = ?1")
	List<Role>findRolesByUsername(String username);
	@Query("SELECT DISTINCT auth FROM Authority auth WHERE auth.account In ?1")
	List<Authority>findAuthoritiesOfAdministrators(List<Account>accounts);
}
