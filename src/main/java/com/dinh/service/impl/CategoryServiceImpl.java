package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinh.dao.CategoryDao;
import com.dinh.entity.Category;
import com.dinh.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao daoC;

	@Override
	public List<Category> findAll() {
		return daoC.findAll();
	}

	@Override
	public Category getOne(String id) {
		return daoC.findById(id).get();
	}

	@Override
	public Category save(Category category) {
		category.setId(category.getId().trim());
		return daoC.save(category);
	}

	@Override
	public void delete(String id) {
		daoC.deleteById(id);
	}

}
