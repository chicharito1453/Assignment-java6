package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinh.dao.OrderDetailDao;
import com.dinh.entity.Order;
import com.dinh.entity.OrderDetail;
import com.dinh.entity.ReportProduct;
import com.dinh.entity.ReportYear;
import com.dinh.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailDao daoOD;

	@Override
	public List<OrderDetail> findByOrderEquals(Order order) {
		return daoOD.findByOrderEquals(order);
	}

	@Override
	public void delete(OrderDetail od) {
		daoOD.delete(od);
	}

	@Override
	public List<ReportProduct> getInventoryByProduct() {
		return daoOD.getInventoryByProduct();
	}

	@Override
	public List<ReportYear> getInventoryByYear() {
		return daoOD.getInventoryByYear();
	}

	@Override
	public List<OrderDetail> findAll() {
		return daoOD.findAll();
	}

}	
