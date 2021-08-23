package com.dinh.service;

import java.util.List;

import com.dinh.entity.Category;

public interface CategoryService {

	List<Category> findAll();

	Category getOne(String id);

	Category save(Category category);

	void delete(String id);

}
