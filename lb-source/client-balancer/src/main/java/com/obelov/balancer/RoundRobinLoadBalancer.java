package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class RoundRobinLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;
	private AtomicInteger nextInt = new AtomicInteger();

	@Override
	public Optional<String> getServer() {
		List<String> servers = healthCheckService.getAvailableServers();

		if(servers.isEmpty()) {
			return Optional.empty();
		}

		int serverIndex = nextInt.incrementAndGet();

		if (serverIndex >= servers.size()) {
			serverIndex = 0;
			nextInt.set(0);
		}

		return Optional.of(servers.get(serverIndex));
	}
}
