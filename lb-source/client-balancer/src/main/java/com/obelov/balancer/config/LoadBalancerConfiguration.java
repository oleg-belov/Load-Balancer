package com.obelov.balancer.config;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class LoadBalancerConfiguration {
	private List<ServerDefinition> servers;
}
