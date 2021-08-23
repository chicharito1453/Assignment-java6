package com.dinh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dinh.dao.AccountDao;
import com.dinh.dao.OrderDao;
import com.dinh.dao.OrderDetailDao;
import com.dinh.entity.Account;
import com.dinh.entity.Order;
import com.dinh.entity.OrderDetail;
import com.dinh.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao daoO;
	@Autowired
	AccountDao daoA;
	@Autowired
	OrderDetailDao daoOD;

	@Override
	public List<Order> findByAccountEquals(Account account) {
		return daoO.findByAccountEquals(account);
	}

	@Override
	public Order getOne(Long id) {
		return daoO.findById(id).get();
	}

	@Override
	public void delete(Order order) {
		daoOD.deleteAll(daoOD.findByOrderEquals(order));
		daoO.delete(order);
	}

	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);
		String username ="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername(); 
		}
		order.setAccount(daoA.findById(username).get());
		daoO.save(order);
		
		TypeReference<List<OrderDetail>>type = new TypeReference<List<OrderDetail>>() {};
		List<OrderDetail>details = mapper.convertValue(orderData.get("orderDetails"), type)
				.stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		daoOD.saveAll(details);
		return order;
	}

	@Override
	public boolean isExist(Long id) {
		return daoO.existsById(id);
	}

	@Override
	public List<Order> findAll() {
		return daoO.findAll();
	}

	@Override
	public Order save(Order order) {
		return daoO.save(order);
	}

}
