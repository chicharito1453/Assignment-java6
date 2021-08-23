package com.dinh.service;

import java.util.List;

import com.dinh.entity.Product;
import com.dinh.entity.Rating;

public interface RatingService {

	List<Rating> findByProductR(Product item);

	Rating save(Rating r);

	Rating findByIdP(Integer idProduct);

}
