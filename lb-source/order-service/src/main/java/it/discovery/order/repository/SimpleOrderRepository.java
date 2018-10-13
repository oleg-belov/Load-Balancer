package it.discovery.order.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import it.discovery.order.model.Order;

@Repository
public class SimpleOrderRepository implements OrderRepository {
	private final Map<Integer, Order> orders = new HashMap<>();

	private int counter = 0;

	@Override
	public Order findById(int id) {
		return orders.get(id);
	}

	@Override
	public List<Order> findAll() {
		return new ArrayList<>(orders.values());
	}

	@Override
	public void save(Order order) {
		if (order.getId() == 0) {
			counter++;
			order.setId(counter);
			orders.put(counter, order);
			System.out.println("*** Order with id=" + order.getId() + " was created");
		} else {
			orders.put(order.getId(), order);
			System.out.println("*** Order with id=" + order.getId() + " was updated");
		}
	}

	@Override
	public boolean delete(int id) {
		if (!orders.containsKey(id)) {
			return false;
		}

		orders.remove(id);
		System.out.println("*** Order with id=" + id + " was deleted");
		return true;
	}

	@Override
	public boolean isEmpty() {
		return orders.isEmpty();
	}

}
