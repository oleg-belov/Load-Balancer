package com.obelov.balancer.definition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerDefinition {

	private String address;
	private boolean alive;
}
