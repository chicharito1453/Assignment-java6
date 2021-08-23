package com.dinh.service;

import java.util.List;

import com.dinh.entity.Account;
import com.dinh.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;

public interface OrderService {

	List<Order> findByAccountEquals(Account account);

	Order getOne(Long id);

	void delete(Order order);

	Order create(JsonNode orderData);

	boolean isExist(Long id);

	List<Order> findAll();

	Order save(Order order);

}
