package it.discovery.order.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
	private int id;
	
	private int bookId;
	
	private double price;
	
	private LocalDateTime created;

}
