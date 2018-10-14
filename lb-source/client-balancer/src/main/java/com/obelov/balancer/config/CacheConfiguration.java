package com.obelov.balancer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheConfiguration {

	private int initialCapacity;

	private int maxCapacity;

	//In seconds
	private int expirationTime;
}
