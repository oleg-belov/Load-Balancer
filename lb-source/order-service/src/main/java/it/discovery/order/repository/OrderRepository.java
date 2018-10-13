package it.discovery.order.repository;

import java.util.List;

import it.discovery.order.model.Order;

public interface OrderRepository {
	Order findById(int id);
	
	List<Order> findAll();
	
	void save(Order Order);
	
	boolean delete(int id);
	
	boolean isEmpty();

}
