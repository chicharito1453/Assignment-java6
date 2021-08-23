package com.dinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinh.entity.Category;

public interface CategoryDao extends JpaRepository<Category, String> {

}
