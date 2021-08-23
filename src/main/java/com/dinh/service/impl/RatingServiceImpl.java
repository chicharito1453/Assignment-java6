package com.dinh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinh.dao.RatingDao;
import com.dinh.entity.Product;
import com.dinh.entity.Rating;
import com.dinh.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	RatingDao daoR;

	@Override
	public List<Rating> findByProductR(Product item) {
		return daoR.findByProductR(item);
	}

	@Override
	public Rating save(Rating r) {
		return daoR.save(r);
	}

	@Override
	public Rating findByIdP(Integer idProduct) {
		return daoR.findByIdP(idProduct);
	}
}
