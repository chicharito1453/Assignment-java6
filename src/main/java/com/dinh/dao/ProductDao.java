package com.dinh.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dinh.entity.Product;
import com.dinh.entity.ReportCategory;

public interface ProductDao extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.id IN ?1 AND p.price <=?2")
	Page<Product>findByCategoryIdAndPriceLessThanEqual(List<String>categories, double price, Pageable pageable);
	@Query("SELECT new ReportCategory(o.category, sum(o.price), count(o)) "
			+ " FROM Product o "
			+ " GROUP BY o.category"
			+ " ORDER BY sum(o.price) DESC")
	List<ReportCategory> getInventoryByCategory();
	List<Product>findByNameContaining(String name);
}
