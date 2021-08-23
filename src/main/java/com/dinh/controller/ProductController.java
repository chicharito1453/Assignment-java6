package com.dinh.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinh.entity.Category;
import com.dinh.entity.Product;
import com.dinh.help.SessionService;
import com.dinh.service.AccountService;
import com.dinh.service.CategoryService;
import com.dinh.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	SessionService serviceS;

	@RequestMapping("/products")
	public String products(@RequestParam("criteria") Optional<String> criteria, Model m) {
		// 8 SP đầu
		List<Category> categoryList = categoryService.findAll();
		List<Order> orders = new ArrayList<Order>();
		Order order1 = new Order(Sort.Direction.DESC, "discount");
		orders.add(order1);
		Order order2 = new Order(Sort.Direction.DESC, criteria.orElse("createDate"));
		orders.add(order2);
		Sort sort = Sort.by(orders);
		Pageable pageable2 = PageRequest.of(0, 8, sort);
		Page<Product> items = productService.findAll(pageable2);
		if (serviceS.get("checkP") == null) {
			serviceS.set("checkP", Double.MAX_VALUE);
		}
		if (serviceS.get("categories") != null) {
			List<String> categories = serviceS.get("categories");
			items = productService.findByCategoryIdAndPriceLessThanEqual(categories, serviceS.get("checkP"), pageable2);
		} else {
			List<String> categories = new ArrayList<>();
			categoryList.forEach(c -> categories.add(c.getId()));
			items = productService.findByCategoryIdAndPriceLessThanEqual(categories, serviceS.get("checkP"), pageable2);
		}
		// 5 SP mới
		Sort sort2 = Sort.by(Direction.DESC, "createDate");
		Pageable pageable = PageRequest.of(0, 5, sort2);
		Page<Product> newItems = productService.findAll(pageable);

		m.addAttribute("ttPage", items.getTotalPages() - 1);
		m.addAttribute("items", items);
		m.addAttribute("newItems", newItems);
		m.addAttribute("criteria", criteria.orElse("createDate"));
		return "user/products";
	}

	@PostMapping("/products")
	public String search(@RequestParam(value = "categories", required = false) List<String> categories,
			@RequestParam(value = "priceP", required = false) List<Double> priceP, Model m) {
		if (categories != null) {
			serviceS.set("categories", categories);
		} else {
			serviceS.remove("categories");
		}
		if (priceP != null) {
			double max = priceP.get(0);
			for (Double d : priceP) {
				if (d > max) {
					max = d;
				}
			}
			serviceS.set("checkP", max);
		} else {
			serviceS.remove("checkP");
		}
		return "redirect:/products";
	}

	@RequestMapping("/products/{criteria}")
	@ResponseBody
	public String listProduct(@RequestParam("page") Integer page, @PathVariable("criteria") Optional<String> criteria,
			Model m) {
		String content = "";
		List<Order> orders = new ArrayList<Order>();
		Order order1 = new Order(Sort.Direction.DESC, "discount");
		Order order2 = new Order(Sort.Direction.DESC, criteria.orElse("createDate"));
		orders.add(order1);
		orders.add(order2);
		Sort sort = Sort.by(orders);
		Pageable pageable = PageRequest.of(page, 8, sort);
		Page<Product> items = productService.findAll(pageable);
		if (serviceS.get("priceP") == null) {
			serviceS.set("priceP", Double.MAX_VALUE);
		}
		if (serviceS.get("categories") != null) {
			items = productService.findByCategoryIdAndPriceLessThanEqual(serviceS.get("categories"),
					serviceS.get("priceP"), pageable);
		} else {
			List<String> categories = new ArrayList<>();
			for (Category c : categoryService.findAll()) {
				categories.add(c.getId());
			}
			items = productService.findByCategoryIdAndPriceLessThanEqual(categories, serviceS.get("priceP"), pageable);
		}
		for (Product p : items.getContent()) {
			String km = "";
			Locale localeVN = new Locale("vi", "VN");
			NumberFormat nf = NumberFormat.getCurrencyInstance(localeVN);
			double realPrice = p.getPrice() - (p.getPrice() * p.getDiscount() / 100);
			String themBnt = "<a onclick=\"callCartAdd("+p.getId()+")\" style=\"position: absolute;bottom: 0px;right: 20px;\" class=\"btn btn-cart\">\r\n"
					+ "								                   Thêm vào giỏ\r\n"
					+ "								                </a>";
			if (!p.getAvailable()) {
				themBnt = "<h3 align=\"center\" style=\"color: red;\">HẾT HÀNG</h3>";
			}
			if (p.getDiscount() > 0) {
				km = "<i class=\"fa fa-gift\" style=\"color: red;\" aria-hidden=\"true\">Khuyến mãi:</i><br>\r\n"
						+ "	                                                	<span class=\"prev-price\">\r\n"
						+ "		                                                    <del>" + nf.format(p.getPrice())
						+ "			                        					</del>\r\n"
						+ "		                                                </span>";
			}
			if (p.getImage() == null) {
				p.setImage("http://www.eurostrada.net/wp-content/uploads/2015/04/Image-Coming-Soon.png");
			} else {
				p.setImage("/assets/images/"+p.getImage());
			}
			content += "<div class=\"col-md-3\">\r\n"
					+ "	                                    <div class=\"product-item\">\r\n"
					+ "	                                        <div style=\"height: 300px;position: relative;\" class=\"product-borde-inner\">\r\n"
					+ "	                                            <a href=\"/productSingle/" + p.getId()
					+ "\">\r\n" + "	                                                <img src=\"" + p.getImage()
					+ "\" class=\"img img-responsive\"/>\r\n"
					+ "	                                            </a> \r\n" + "	\r\n"
					+ "	                                            <div class=\"product-price\">\r\n"
					+ "	                                                <a href=\"/productSingle/" + p.getId()
					+ "\">" + p.getName() + "</a><br>\r\n"
					+ "	                                                <div>\r\n" + km
					+ "	                                                </div>\r\n"
					+ "	                                                <span class=\"current-price\">\r\n"
					+ "	                                                    " + nf.format(realPrice) + " đ\r\n"
					+ "	                                                </span>\r\n"
					+ "	                                            </div>\r\n" + themBnt
					+ "	                                            <div class=\"clear\"></div>\r\n"
					+ "	                                        </div>\r\n"
					+ "	                                    </div> \r\n"
					+ "	                                </div><!-- End Latest products[single] -->";
		}
		if (items.getTotalPages() < page + 1) {
			content = "";
		}
		return content;
	}

}
