package com.obelov.balancer;

import com.obelov.balancer.healthcheck.HealthCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StubLoadBalancer implements LoadBalancer {

	private final HealthCheckService healthCheckService;

	@Override
	public String getServer() {
		if (healthCheckService.getAvailableServers().isEmpty()) {
			log.error("No servers to choose");
		}

		return healthCheckService.getAvailableServers().get(0);
	}
}