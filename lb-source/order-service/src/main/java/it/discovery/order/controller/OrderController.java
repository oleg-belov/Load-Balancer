package it.discovery.order.controller;

import it.discovery.order.api.BookClient;
import it.discovery.order.model.Order;
import it.discovery.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BookClient bookClient;

	@PostMapping(path = "/{bookId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void makeOrder(@PathVariable int bookId) {
		Order order = new Order();

		double price = this.bookClient.getPrice(bookId);

		order.setBookId(bookId);
		order.setPrice(price);

		orderRepository.save(order);
	}

}
