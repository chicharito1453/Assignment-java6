package com.dinh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dinh.entity.Product;
import com.dinh.entity.Rating;

public interface RatingDao extends JpaRepository<Rating, Integer> {
	List<Rating>findByProductR(Product product);
	@Query("SELECT r FROM Rating r WHERE r.productR.id = ?1")
	Rating findByIdP(int id);
}
