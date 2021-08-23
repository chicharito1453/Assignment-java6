package com.dinh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinh.entity.Account;
import com.dinh.entity.Order;
import com.dinh.entity.OrderDetail;
import com.dinh.service.AccountService;
import com.dinh.service.OrderDetailService;
import com.dinh.service.OrderService;
import com.dinh.service.ProductService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	ProductService productService;
	@Autowired
	AccountService accountService;

	@RequestMapping("/order")
	public String form(Model m) {
		List<Order> items = orderService.findByAccountEquals(accountService.getOne(accountService.getUserName()));
		boolean test= false;
		for (Order order : items) {
			if(!order.isStatus()) {
				test=true;
				break;
			}
		}
		m.addAttribute("test", test);
		m.addAttribute("items", items);
		return "user/order";
	}

	@RequestMapping("/order/remove/{id}")
	public String removeOrder(@PathVariable("id") Long id) {
		Order order = orderService.getOne(id);
		orderService.delete(order);
		return "redirect:/order";
	}

	@RequestMapping("/orderDetail/{id}")
	public String orderDetail(@PathVariable("id") String idOrder, Model m) {
		try {
			Long id = Long.parseLong(idOrder);
			if (orderService.isExist(id)) {
				Account accountOrder = orderService.getOne(id).getAccount();
				if (!accountService.getUserName().equals(accountOrder.getUsername())) {
					return "redirect:/products";
				}
				List<OrderDetail> items = orderDetailService.findByOrderEquals(orderService.getOne(id));
				if (items.size() == 0) {
					m.addAttribute("error", "Dữ liệu chi tiết về đơn hàng đã bị xóa!");
				}
				m.addAttribute("totalPrice", orderService.getOne(id).getTotal());
				m.addAttribute("items", items);
			}
		} catch (Exception e) {
			return "user/orderDetails";
		}
		return "user/orderDetails";
	}

}
