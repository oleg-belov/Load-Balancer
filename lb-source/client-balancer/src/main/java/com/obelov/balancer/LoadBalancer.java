package com.obelov.balancer;

import com.obelov.balancer.definition.ServerDefinition;

import java.util.List;

public interface LoadBalancer {

	List<ServerDefinition> getServers();

	ServerDefinition getServer();
}
