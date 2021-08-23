package com.dinh.service;

import java.util.List;

import com.dinh.entity.Order;
import com.dinh.entity.OrderDetail;
import com.dinh.entity.ReportProduct;
import com.dinh.entity.ReportYear;

public interface OrderDetailService {

	List<OrderDetail> findByOrderEquals(Order order);

	void delete(OrderDetail od);

	List<ReportProduct> getInventoryByProduct();

	List<ReportYear> getInventoryByYear();

	List<OrderDetail> findAll();

}
