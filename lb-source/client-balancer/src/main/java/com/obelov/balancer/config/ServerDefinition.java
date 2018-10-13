package com.obelov.balancer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerDefinition {

	private String address;
	private boolean alive;
}
