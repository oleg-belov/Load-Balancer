package com.obelov.balancer;

import com.obelov.balancer.config.ServerDefinition;

@FunctionalInterface
public interface HealthCheckStrategy {

	void healthCheck(ServerDefinition server);
}