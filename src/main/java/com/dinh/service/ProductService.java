package com.dinh.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dinh.entity.Product;
import com.dinh.entity.ReportCategory;

public interface ProductService {

	List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);

	List<Product> findAll(Sort sort2);
	
	Product getOne(Integer id);

	Page<Product> findByCategoryIdAndPriceLessThanEqual(List<String>categories, double price, Pageable pageable);

	boolean isExist(Integer idProduct);
	
	List<Product>findByNameContaining(String name);

	Product save(Product p);

	void delete(Integer idProduct);

	List<ReportCategory> getInventoryByCategory();

}
