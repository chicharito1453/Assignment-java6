package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinh.dao.OrderDao;
import com.dinh.dao.OrderDetailDao;
import com.dinh.dao.ProductDao;
import com.dinh.dao.RatingDao;
import com.dinh.entity.Product;
import com.dinh.entity.ReportCategory;
import com.dinh.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao daoP;
	@Autowired
	OrderDao daoO;
	@Autowired
	OrderDetailDao daoOD;
	@Autowired
	RatingDao daoR;

	@Override
	public List<Product> findAll() {
		return daoP.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return daoP.findAll(pageable);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return daoP.findAll(sort);
	}

	@Override
	public Product getOne(Integer id) {
		return daoP.findById(id).get();
	}

	@Override
	public Page<Product> findByCategoryIdAndPriceLessThanEqual(List<String> categories, double price,
			Pageable pageable) {
		return daoP.findByCategoryIdAndPriceLessThanEqual(categories, price, pageable);
	}

	@Override
	public boolean isExist(Integer idProduct) {
		return daoP.existsById(idProduct);
	}

	@Override
	public List<Product> findByNameContaining(String name) {
		return daoP.findByNameContaining(name);
	}

	@Override
	public Product save(Product p) {
		return daoP.save(p);
	}

	@Override
	public void delete(Integer idProduct) {
		daoP.deleteById(idProduct);
//		Product p = daoP.findById(idProduct).get();
//		List<OrderDetail>list=daoOD.findByProductEquals(p);//Danh sách OrderDetail của product
//		List<Rating>listR = daoR.findByProductR(p);//Danh sách rating của product
//		List<Order>listOrder = new ArrayList<Order>();
//		list.forEach(orderDetail -> {
//			listOrder.add(orderDetail.getOrder());//Danh sách Order có chứa product
//		});
//		list.clear();
//		listOrder.forEach(order ->list.addAll(daoOD.findByOrderEquals(order)));//Orderdetail của product + orderDetail nằm cùng Order vs product
//		daoOD.deleteAll(list);
//		//Xóa những Order chưa giao dịch
//		listOrder.forEach(order -> {
//			if(!order.isStatus()) {
//				daoO.delete(order);
//			}
//		});
//		daoR.deleteAll(listR);
//		daoP.deleteById(idProduct);
	}

	@Override
	public List<ReportCategory> getInventoryByCategory() {
		return daoP.getInventoryByCategory();
	}

}
