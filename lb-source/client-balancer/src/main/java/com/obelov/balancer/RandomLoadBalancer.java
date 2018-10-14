package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;

	private Random random = new Random();

	@Override
	public String getServer() {
		List<String> servers =
				healthCheckService.getAvailableServers();

		return servers.get(random.nextInt(servers.size()));
	}
}
