package com.dinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinh.entity.Product;
import com.dinh.entity.Rating;
import com.dinh.service.AccountService;
import com.dinh.service.ProductService;
import com.dinh.service.RatingService;

@Controller
public class ProductDetailsController {

	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;
	@Autowired
	RatingService ratingService;

	@RequestMapping("/productSingle/{id}")
	public String product(@PathVariable("id") String idProduct, Model m) {
		try {
			int id = Integer.parseInt(idProduct);
			if (productService.isExist(id)) {
				Product item = productService.getOne(id);
				List<Rating> listR = ratingService.findByProductR(item);
				if (listR.size() > 0) {
					int total = listR.stream().mapToInt(r -> r.getStars()).sum();
					m.addAttribute("rate", "Đánh giá: " + Math.round((total / listR.size()) * 100) / 100 + " / 5 - "
							+ listR.size() + " lượt đánh giá");
				} else {
					m.addAttribute("rate", "Chưa có lượt đánh giá nào");
				}
				m.addAttribute("item", item);
			}
		} catch (Exception e) {
			return "user/product_single";
		}
		return "user/product_single";
	}

	@RequestMapping("/productSingle/like/{idProduct}")
	@ResponseBody
	public String rating(@PathVariable("idProduct") Integer idProduct, @RequestParam("stars") Integer stars) {
		if (accountService.getUserName() == null) {
			return "Đăng nhập để đánh giá sản phẩm!";
		} else {
			if (productService.isExist(idProduct)) {
				Rating r = new Rating();
				r.setAccountR(accountService.getOne(accountService.getUserName()));
				r.setProductR(productService.getOne(idProduct));
				r.setStars(stars);
				ratingService.save(r);
			} else {
				Rating r = ratingService.findByIdP(idProduct);
				r.setStars(stars);
				ratingService.save(r);
			}
			return "Cám ơn bạn đã đóng góp ý kiến!";
		}
	}
}
