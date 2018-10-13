package com.obelov.balancer;

import com.obelov.balancer.definition.ServerDefinition;

public interface HealthCheckStrategy {

	void healthCheck(ServerDefinition server);
}