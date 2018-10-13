package com.obelov.balancer;

import com.obelov.balancer.config.LoadBalancerConfiguration;
import com.obelov.balancer.config.ServerDefinition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StubLoadBalancer implements LoadBalancer {

	private final LoadBalancerConfiguration loadBalancerConfiguration;

	@Override
	public ServerDefinition getServer() {
		return loadBalancerConfiguration.getServers().get(0);
	}
}