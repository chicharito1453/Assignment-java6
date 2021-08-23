package com.dinh.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dinh.entity.Order;
import com.dinh.entity.OrderDetail;
import com.dinh.entity.Product;
import com.dinh.entity.ReportProduct;
import com.dinh.entity.ReportYear;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Long> {
	List<OrderDetail>findByOrderEquals(Order order);
	List<OrderDetail>findByProductEquals(Product product);
	@Query("SELECT new ReportProduct(o.product, sum(o.price), count(o)) "
			+ " FROM OrderDetail o WHERE o.order.status = 1 "
			+ " GROUP BY o.product"
			+ " ORDER BY sum(o.price) DESC")
	List<ReportProduct> getInventoryByProduct();
	
	@Query("SELECT new ReportYear(year(o.order.createDate), sum(o.order.total), count(o)) "
			+ " FROM OrderDetail o WHERE o.order.status = 1 "
			+ " GROUP BY year(o.order.createDate)"
			+ " ORDER BY year(o.order.createDate) DESC")
	List<ReportYear> getInventoryByYear();
}
