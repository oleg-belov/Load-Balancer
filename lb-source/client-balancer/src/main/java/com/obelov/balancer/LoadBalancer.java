package com.obelov.balancer;

import com.obelov.balancer.config.ServerDefinition;

public interface LoadBalancer {

	ServerDefinition getServer();
}
