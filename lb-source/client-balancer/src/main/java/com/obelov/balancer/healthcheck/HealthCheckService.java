package com.obelov.balancer.healthcheck;

import java.util.List;

@FunctionalInterface
public interface HealthCheckService {

	List<String> getAvailableServers();
}
