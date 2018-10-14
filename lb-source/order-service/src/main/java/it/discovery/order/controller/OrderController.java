package it.discovery.order.controller;

import com.obelov.balancer.rest.service.RestService;
import it.discovery.order.model.Order;
import it.discovery.order.repository.OrderRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestService restService;

	@Value("${load-balancer.url}")
	private String baseUrl;

	@PostMapping(path = "/{bookId}")
	@ResponseStatus(HttpStatus.CREATED)
	public Order makeOrder(@PathVariable int bookId) {
		Order order = new Order();

		Map<String, Object> book = restService
				.getForObject(baseUrl + "/book/" + bookId, Map.class);

		double price = NumberUtils.toDouble(book.get("price").toString());

		order.setBookId(bookId);
		order.setPrice(price);

		orderRepository.save(order);

		return order;
	}

}
