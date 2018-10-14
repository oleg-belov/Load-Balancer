package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class StubLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;

	@Override
	public Optional getServer() {
		if (healthCheckService.getAvailableServers().isEmpty()) {
			log.error("No servers to choose");
			return Optional.empty();
		}

		return Optional.of(healthCheckService.getAvailableServers().get(0));
	}
}