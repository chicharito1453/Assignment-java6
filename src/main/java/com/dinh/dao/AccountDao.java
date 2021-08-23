package com.dinh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dinh.entity.Account;

public interface AccountDao extends JpaRepository<Account, String> {
	@Query("SELECT o FROM Account o WHERE o.verifycode = ?1")
	public Account findByVerifyCode(String code);
	@Query("SELECT DISTINCT a.account FROM Authority a")
	public List<Account>getAdministrators();
}
