package com.obelov.balancer;

import com.obelov.balancer.config.ServerDefinition;

public interface HealthCheckStrategy {

	void healthCheck(ServerDefinition server);
}