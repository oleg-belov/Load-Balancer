package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
public class RandomLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;

	private Random random = new Random();

	@Override
	public Optional getServer() {
		List<String> servers =
				healthCheckService.getAvailableServers();

		if(servers.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(servers.get(random.nextInt(servers.size())));
	}
}
