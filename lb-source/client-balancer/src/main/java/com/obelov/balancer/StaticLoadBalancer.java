package com.obelov.balancer;

import com.obelov.balancer.definition.ServerDefinition;

import java.util.List;

public class StaticLoadBalancer implements LoadBalancer {

	private List<ServerDefinition> servers;

	@Override
	public List<ServerDefinition> getServers() {
		return servers;
	}

	@Override
	public ServerDefinition getServer() {
		return null;
	}
}
