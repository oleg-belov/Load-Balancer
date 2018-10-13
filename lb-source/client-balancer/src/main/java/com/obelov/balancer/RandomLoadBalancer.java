package com.obelov.balancer;

import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.ServerDefinition;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomLoadBalancer implements LoadBalancer {

	private final LoadBalancerConfiguration loadBalancerConfiguration;

	private Random random = new Random();

	@Override
	public ServerDefinition getServer() {
		List<ServerDefinition> servers = loadBalancerConfiguration.getServers();

		return servers.get(random.nextInt(servers.size()));
	}
}
