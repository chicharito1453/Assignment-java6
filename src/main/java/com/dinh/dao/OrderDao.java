package com.dinh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinh.entity.Account;
import com.dinh.entity.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
	List<Order>findByAccountEquals(Account account);
	Order findByOrderDetailsIsNull();
}
